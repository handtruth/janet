import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.invocation.Gradle
import org.gradle.api.publish.Publication

fun Project.submodule(name: String): Project {
    return project(":${rootProject.name}-$name")
}

private var mAllPublications: NamedDomainObjectContainer<Publication>? = null

val Gradle.allPublications: NamedDomainObjectContainer<Publication> get() {
    val publications = mAllPublications
    if (publications != null) {
        return publications
    }
    val newPublications = this.rootProject.objects.domainObjectContainer(Publication::class.java)
    mAllPublications = newPublications
    return newPublications
}
