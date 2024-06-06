import org.gradle.api.tasks.testing.logging.TestExceptionFormat

plugins {
    id("com.handtruth.gradle.base")
    java
    jacoco
    id("info.solidsoft.pitest")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

pitest {
    targetClasses.addAll("$group.${project.name.removePrefix(rootProject.name + "-")}.*")
    junit5PluginVersion.set("1.2.1")
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0-M2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0-M2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
    }
}
