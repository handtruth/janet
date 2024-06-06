plugins {
    id("com.handtruth.gradle.base")
    `java-platform`
    `maven-publish`
}

dependencies {
    constraints {
        api("com.github.spotbugs:spotbugs-annotations:4.8.5")
        api("org.slf4j:slf4j-api:2.0.13")
    }
}

publishing {
    publications {
        create<MavenPublication>("main") {
            from(components["javaPlatform"])
        }
    }
}
