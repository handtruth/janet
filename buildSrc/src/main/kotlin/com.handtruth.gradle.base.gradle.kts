plugins {
    base
    id("com.gladed.androidgitversion")
}

androidGitVersion {
    prefix = "v"
}

version = androidGitVersion.name()

repositories {
    mavenCentral()
}

tasks.withType<AbstractArchiveTask> {
    isPreserveFileTimestamps = false
    isReproducibleFileOrder = true
}
