import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("io.github.goooler.shadow") version "8.1.7" // TODO: Temp
    id("com.modrinth.minotaur") version "2.+" // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"

dependencies {
    implementation(project(":fabric", configuration = "shadow"))
    implementation(project(":bukkit", configuration = "shadow"))
}

tasks {
    jar {
        subprojects {
            dependsOn("${project.name}:build")
        }
    }

    shadowJar {
        archiveClassifier = ""
        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}

modrinth {
    autoAddDependsOn = false
    token = System.getenv("MODRINTH_TOKEN")
    projectId = "pl3xmap"
    versionName = "${project.version}"
    versionNumber = "${project.version}"
    versionType = "beta"
    uploadFile = rootProject.layout.buildDirectory.file("libs/${rootProject.name}-${project.version}.jar").get()
    //additionalFiles.addAll([
    //        rootProject.layout.buildDirectory.file("libs/${rootProject.name}-${project.version}-javadoc.jar").get(),
    //        rootProject.layout.buildDirectory.file("libs/${rootProject.name}-${project.version}-sources.jar").get()
    //])
    gameVersions.addAll(listOf("${rootProject.properties["minecraftVersion"]}"))
    loaders.addAll(listOf("bukkit", "fabric", /*"forge",*/ "paper", "purpur", "quilt", "spigot", "folia"))
    changelog = System.getenv("COMMIT_MESSAGE")
    dependencies {
        required.project("fabric-api")
         //optional.project(
         //    "pl3xmap-banners",
         //    "pl3xmap-claims",
         //    "pl3xmap-mobs",
         //    "pl3xmap-signs",
         //    "pl3xmap-warps",
         //    "deathspots",
         //)
    }
}