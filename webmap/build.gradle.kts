import com.github.gradle.node.npm.task.NpxTask

plugins {
    id("java")
    id("com.github.node-gradle.node") version("7.0.1")
}

tasks.named("clean") {
    delete("$projectDir/dist")
}

tasks.named("compileJava") {
    dependsOn(buildWebmap)
}

val buildWebmap = tasks.register<NpxTask>("buildWebmap") {
    command = "webpack"
    dependsOn(tasks.named("npmInstall"))
}

sourceSets {
    main {
        java {
            resources {
                srcDir("$projectDir")
                include("web/**")
            }
        }
    }
}
