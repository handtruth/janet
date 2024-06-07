plugins {
    id("com.handtruth.gradle.java")
    id("com.handtruth.gradle.publish")
    `java-library`
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        val publication = create<MavenPublication>("main") {
            from(components["java"])
        }
        gradle.allPublications.add(publication)
    }
}
