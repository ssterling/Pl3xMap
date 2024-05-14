import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("io.github.goooler.shadow") version "8.1.7" // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"

dependencies {
    implementation(project(":fabric", configuration = "shadow"))
    implementation(project(":bukkit", configuration = "shadow"))
}

tasks {
    shadowJar {
        archiveClassifier = ""

        mergeServiceFiles()
    }

    build {
        dependsOn(shadowJar)
    }
}
