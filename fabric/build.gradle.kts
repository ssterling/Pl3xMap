import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import net.fabricmc.loom.task.RemapJarTask
import org.gradle.jvm.tasks.Jar

plugins {
    id("fabric-loom") version("1.6-SNAPSHOT") // TODO: Temp
    id("io.github.goooler.shadow") version "8.1.7" // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"
project.group = "net.pl3x.map.fabric"

loom {
    mixin {
        defaultRefmapName = "pl3xmap.refmap.json"
    }
    accessWidenerPath = file("src/main/resources/pl3xmap.accesswidener")
    runConfigs.configureEach {
        ideConfigGenerated(true)
    }
}

repositories {
    maven("https://oss.sonatype.org/content/repositories/snapshots/") {
        name = "sonatype-snapshots"
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
    maven("https://maven.fabricmc.net/")
    maven("https://jitpack.io")
}

dependencies {
    minecraft("com.mojang:minecraft:${rootProject.properties["minecraftVersion"]}")
    mappings(loom.officialMojangMappings())

    implementation(project(path = ":core", configuration = "shadow"))

    modImplementation("net.fabricmc:fabric-loader:${rootProject.properties["fabricLoaderVersion"]}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${rootProject.properties["fabricApiVersion"]}")

    include(modImplementation("org.incendo:cloud-fabric:${rootProject.properties["cloudVersion"]}")!!)
    include(modImplementation("net.kyori:adventure-platform-fabric:${rootProject.properties["adventureFabricVersion"]}")!!)
}

tasks {
    shadowJar {
        dependsOn(remapJar)
        from(remapJar)

        mergeServiceFiles()

        dependencies {
            include(project(":core"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}

tasks.withType<ProcessResources> {
    inputs.properties("name" to rootProject.name)
    inputs.properties("group" to project.group)
    inputs.properties("version" to project.version)
    inputs.properties("authors" to project.properties["authors"])
    inputs.properties("description" to rootProject.properties["description"])
    inputs.properties("fabricApiVersion" to rootProject.properties["fabricApiVersion"])
    inputs.properties("fabricLoaderVersion" to rootProject.properties["fabricLoaderVersion"])
    inputs.properties("minecraftVersion" to rootProject.properties["minecraftVersion"])
    inputs.properties("website" to rootProject.properties["website"])
    inputs.properties("sources" to rootProject.properties["sources"])
    inputs.properties("issues" to rootProject.properties["issues"])

    filesMatching("fabric.mod.json") {
        expand(inputs.properties)
    }

    // replace whole array with authors
    doLast {
        val fabricJsonFile = outputs.files.singleFile.resolve("fabric.mod.json")
        @Suppress("UNCHECKED_CAST")
        val jsonContent = JsonSlurper().parse(fabricJsonFile) as MutableMap<String, Any>
        jsonContent["authors"] = JsonSlurper().parseText(project.properties["authors"].toString()) as Any

        fabricJsonFile.writeText(JsonBuilder(jsonContent).toPrettyString())
    }
}
