plugins {
    id("com.handtruth.gradle.java.library")
}

dependencies {
    implementation(platform(submodule("platform")))

    compileOnly("com.github.spotbugs:spotbugs-annotations")
    implementation("org.slf4j:slf4j-api")
}
