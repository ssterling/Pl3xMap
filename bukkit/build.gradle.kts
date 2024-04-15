import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java")
    id("io.papermc.paperweight.userdev") version("1.5.11") // TODO: Temp
    id("com.github.johnrengelman.shadow") version("8.1.1") // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"
project.group = "net.pl3x.map.bukkit"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
}

dependencies {
//    implementation("net.kyori:adventure-api:${rootProject.properties["adventureVersion"]}")
//    implementation("net.kyori:adventure-text-minimessage:${rootProject.properties["adventureVersion"]}")
//    implementation("net.kyori:adventure-text-serializer-plain:${rootProject.properties["adventureVersion"]}")
    implementation(project(":core"))

    implementation("cloud.commandframework:cloud-core:${rootProject.properties["cloudVersion"]}")
    implementation("cloud.commandframework:cloud-brigadier:${rootProject.properties["cloudVersion"]}")
    implementation("cloud.commandframework:cloud-paper:${rootProject.properties["cloudVersion"]}")

    implementation("net.kyori:adventure-platform-bukkit:${rootProject.properties["adventureBukkitVersion"]}") // TODO: temp

//    implementation("com.github.Querz:NBT:${rootProject.properties["querzNbtVersion"]}")
//    implementation("com.github.Carleslc.Simple-YAML:Simple-Yaml:${rootProject.properties["simpleYamlVersion"]}") {
//        exclude("org.yaml", "snakeyaml")
//    }

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:${rootProject.properties["bukkitVersion"]}")
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
        //"cloud.commandframework", // do not relocate
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
    dependsOn(tasks.named("reobfJar"))
}

tasks.withType<ProcessResources> {
    inputs.properties("name" to rootProject.name)
    inputs.properties("group" to project.group)
    inputs.properties("version" to project.version)
    inputs.properties("authors" to project.properties["authors"])
    inputs.properties("description" to project.properties["description"])
    inputs.properties("website" to project.properties["website"])

    filesMatching("plugin.yml") {
        expand(inputs.properties)
    }
}
