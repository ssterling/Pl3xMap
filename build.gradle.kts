import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("java-library")
    id("io.github.goooler.shadow") version "8.1.7" // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"

dependencies {
    api(project(":fabric", configuration = "shadow"))
    api(project(":bukkit", configuration = "shadow"))
}

tasks {
    shadowJar {
        subprojects
            .filter { it.name != "webmap" }
            .forEach { dependsOn(":${it.name}:shadowJar") }

        archiveClassifier = ""

        mergeServiceFiles()
        exclude(
            "META-INF/LICENSE",
            "META-INF/LICENSE.txt"
        )
    }

    build {
        dependsOn(shadowJar)
    }
}
