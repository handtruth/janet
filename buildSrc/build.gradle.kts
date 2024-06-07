plugins {
    `kotlin-dsl`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

dependencies {
    implementation("info.solidsoft.gradle.pitest:gradle-pitest-plugin:1.15.0")
    implementation("com.gladed.androidgitversion:gradle-android-git-version:0.4.14")
    implementation("org.eclipse.jgit:org.eclipse.jgit:6.8.0.202311291450-r")
}
