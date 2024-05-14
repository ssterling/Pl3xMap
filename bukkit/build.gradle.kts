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
    implementation(project(":core", configuration = "shadow"))

    implementation("org.incendo:cloud-brigadier:${rootProject.properties["cloudVersion"]}")
    implementation("org.incendo:cloud-paper:${rootProject.properties["cloudVersion"]}")

    implementation("net.kyori:adventure-platform-bukkit:${rootProject.properties["adventureBukkitVersion"]}") // TODO: temp

    paperweightDevelopmentBundle("io.papermc.paper:dev-bundle:${rootProject.properties["bukkitVersion"]}")
}

tasks {
    reobfJar {
        dependsOn(shadowJar)
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
