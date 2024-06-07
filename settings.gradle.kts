plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "janet"

fun submodule(name: String) {
    val projectName = "${rootProject.name}-$name"
    include(projectName)
    project(":$projectName").projectDir = file("modules/$name")
}

submodule("bom")
submodule("core")
submodule("platform")
