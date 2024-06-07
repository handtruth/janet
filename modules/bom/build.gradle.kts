plugins {
    id("com.handtruth.gradle.publish")
    `java-platform`
}

dependencies.constraints {
    gradle.allPublications.all {
        if (this is MavenPublication) {
            api("$groupId:$artifactId:$version")
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("main") {
            from(components["javaPlatform"])
        }
    }
}
