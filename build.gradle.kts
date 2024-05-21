plugins {
    id("java-library")
    id("com.modrinth.minotaur") version "2.+" // TODO: Temp
    id("net.kyori.indra.git") version "2.1.1" // TODO: Temp
}

val buildNum = System.getenv("NEXT_BUILD_NUMBER") ?: "TEMP" // TODO: Temp
project.group = "net.pl3x.map"
project.version = "${rootProject.properties["minecraftVersion"]}-$buildNum"

tasks {
    jar {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE

        subprojects {
            dependsOn(project.tasks.build)
        }

        archiveClassifier = ""

        val manifestFiles = mutableSetOf<FileTree>();
        from(layout.files(subprojects.filter({ it.name != "webmap" && it.name != "core" }).map {
            val regularFile = it.layout.buildDirectory.file("libs/${it.name}-${it.version}.jar").get()
            val zipTree = zipTree(regularFile)
            manifestFiles.add(zipTree)
            zipTree
        })) {
            exclude("META-INF/MANIFEST.MF")
        }

        manifestFiles.forEach {
            it.matching { include("META-INF/MANIFEST.MF") }.files.forEach {
                manifest.from(it)
            }
        }

        // TODO: move to core subproject
        manifest {
            attributes["Main-Class"] = "${project.group}.core.Pl3xMap"
            attributes["Git-Commit"] = (if (indraGit.isPresent) indraGit.commit()?.name() ?: "" else "").substring(0, 7)
        }
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