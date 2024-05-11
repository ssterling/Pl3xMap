import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("io.github.goooler.shadow") version "8.1.7" // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"

java {
    withJavadocJar()
    withSourcesJar()
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "oss-sonatype-snapshots"
        mavenContent {
            snapshotsOnly()
        }
    }
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/") {
        name = "s01-sonatype-snapshots"
        mavenContent {
            snapshotsOnly()
        }
    }
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(project(":webmap"))

    compileOnly("org.apache.logging.log4j:log4j-core:${rootProject.properties["log4jVersion"]}")

    compileOnly("org.jetbrains:annotations:24.1.0")

    implementation("io.undertow:undertow-core:${rootProject.properties["undertowVersion"]}")

    implementation("org.incendo:cloud-core:${rootProject.properties["cloudVersion"]}")
    implementation("org.incendo:cloud-brigadier:${rootProject.properties["cloudVersion"]}")
    implementation("org.incendo:cloud-paper:${rootProject.properties["cloudVersion"]}")
    implementation("org.incendo:cloud-processors-confirmation:1.0.0-beta.2")
    implementation("org.incendo:cloud-minecraft-extras:${rootProject.properties["cloudVersion"]}") {
        exclude("net.kyori", "*")
    }

    implementation("net.kyori:adventure-api:${rootProject.properties["adventureVersion"]}")
    implementation("net.kyori:adventure-text-minimessage:${rootProject.properties["adventureVersion"]}")
    implementation("net.kyori:adventure-text-serializer-plain:${rootProject.properties["adventureVersion"]}")
    implementation("net.kyori:adventure-platform-bukkit:${rootProject.properties["adventureBukkitVersion"]}") // TODO: temp

    implementation("com.github.ben-manes.caffeine:caffeine:${rootProject.properties["caffeineVersion"]}")
    implementation("com.github.Querz:NBT:${rootProject.properties["querzNbtVersion"]}")
    implementation("com.github.Carleslc.Simple-YAML:Simple-Yaml:${rootProject.properties["simpleYamlVersion"]}") {
        exclude("org.yaml", "snakeyaml")
    }

    // provided by mojang
    compileOnly("com.google.code.gson:gson:${rootProject.properties["gsonVersion"]}")
    compileOnly("com.google.guava:guava:${rootProject.properties["guavaVersion"]}")
}

tasks.withType<ShadowJar> {
    archiveBaseName = "${rootProject.name}-${project.name}"
    archiveClassifier = ""

    mergeServiceFiles()
    exclude(
        "META-INF/LICENSE",
        "META-INF/LICENSE.txt"
    )

    arrayOf(
        //"org.incendo", // do not relocate
        "com.github.benmanes.caffeine.cache",
        "com.github.Carleslc.Simple-YAML",
        "com.google.errorprone.annotations",
        "com.luciad",
        //"io.leangen.geantyref", // do not relocate!
        "io.undertow",
        //"net.kyori", // do not relocate!
        "net.querz",
        "org.checkerframework",
        "org.jboss",
        "org.simpleyaml",
        "org.wildfly",
        "org.xnio",
        "org.yaml.snakeyaml",
    ).forEach { it -> relocate(it, "libs.$it") }
}

tasks.named("build") {
    dependsOn(tasks.withType<ShadowJar>())
}


tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
    (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    title = "${rootProject.name}-${project.version} API"
}
