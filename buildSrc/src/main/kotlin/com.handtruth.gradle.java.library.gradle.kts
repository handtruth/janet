import org.gradle.kotlin.dsl.`maven-publish`

plugins {
    id("com.handtruth.gradle.java")
    `java-library`
    `maven-publish`
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("main") {
            from(components["java"])
        }
    }
}
