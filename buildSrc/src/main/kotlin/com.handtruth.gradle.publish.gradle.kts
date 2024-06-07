plugins {
    id("com.handtruth.gradle.base")
    `maven-publish`
}

publishing {
    repositories {
        maven {
            name = "Local"
            url = uri("file://${rootProject.layout.buildDirectory.dir("repo").get().asFile.path}")
        }
        maven {
            name = "GitHub"
            url = uri("https://maven.pkg.github.com/handtruth/janet")
            credentials(PasswordCredentials::class) {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        val urlPrefix = System.getenv("CI_API_V4_URL")
        if (urlPrefix != null) {
            maven {
                name = "GitLab"
                val projectId = System.getenv("CI_PROJECT_ID")
                url = uri("$urlPrefix/projects/$projectId/packages/maven")
                credentials(HttpHeaderCredentials::class) {
                    name = "Job-Token"
                    value = System.getenv("CI_JOB_TOKEN")
                }
                authentication {
                    create<HttpHeaderAuthentication>("header")
                }
            }
        }
    }
}
