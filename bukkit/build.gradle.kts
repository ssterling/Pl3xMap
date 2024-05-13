import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version("1.7.1") // TODO: Temp
    id("io.github.goooler.shadow") version "8.1.7" // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"
project.group = "net.pl3x.map.bukkit"

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
//    implementation("net.kyori:adventure-api:${rootProject.properties["adventureVersion"]}")
//    implementation("net.kyori:adventure-text-minimessage:${rootProject.properties["adventureVersion"]}")
//    implementation("net.kyori:adventure-text-serializer-plain:${rootProject.properties["adventureVersion"]}")
    implementation(project(":core"))

    implementation("org.incendo:cloud-core:${rootProject.properties["cloudVersion"]}")
    implementation("org.incendo:cloud-brigadier:${rootProject.properties["cloudVersion"]}")
    implementation("org.incendo:cloud-paper:${rootProject.properties["cloudVersion"]}")

    implementation("net.kyori:adventure-platform-bukkit:${rootProject.properties["adventureBukkitVersion"]}") // TODO: temp

//    implementation("com.github.Querz:NBT:${rootProject.properties["querzNbtVersion"]}")
//    implementation("com.github.Carleslc.Simple-YAML:Simple-Yaml:${rootProject.properties["simpleYamlVersion"]}") {
//        exclude("org.yaml", "snakeyaml")
//    }

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:${rootProject.properties["bukkitVersion"]}")
}

tasks {
    shadowJar {
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

    build {
        dependsOn(reobfJar)
    }

    processResources {
        inputs.properties(mapOf(
            "name" to rootProject.name,
            "group" to project.group,
            "version" to project.version,
            "authors" to project.properties["authors"],
            "description" to project.properties["description"],
            "website" to project.properties["website"]
        ))

        filesMatching("plugin.yml") {
            expand(inputs.properties)
        }
    }
}
