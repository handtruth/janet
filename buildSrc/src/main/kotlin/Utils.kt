import org.gradle.api.Project

fun Project.submodule(name: String): Project {
    return project(":${rootProject.name}-$name")
}
