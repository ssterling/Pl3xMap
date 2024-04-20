import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("java")
    id("com.github.node-gradle.node") version("7.0.1")
}

tasks.named("clean") {
    delete("$projectDir/dist")
}

val buildWebmap = tasks.register<NpxTask>("buildWebmap") {
    dependsOn(tasks.named("npmInstall"))
    command = "webpack"

    inputs.files(listOf(
        "package.json",
        "package-lock.json",
        "tsconfig.json",
        "webpack.config.js",
    ))
    inputs.dir("src")
    inputs.dir("public")
//    inputs.dir(fileTree("node_modules").exclude(".cache"))
    outputs.dir("dist")
}

sourceSets {
    main {
        java {
            resources {
                srcDir("$projectDir")
                include("dist/**")
            }
        }
    }
}

tasks.withType<ProcessResources> {
    dependsOn(buildWebmap)

    doLast {
        val destinationDir = outputs.files.singleFile.resolve("web")
        outputs.files.singleFile.resolve("dist").renameTo(destinationDir)
    }
}
