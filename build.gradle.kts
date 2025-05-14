import com.vanniktech.maven.publish.SonatypeHost

plugins {
  kotlin("jvm") version "1.9.22"
  alias(libs.plugins.ktlint)
  alias(libs.plugins.vanniktech)
}

group = "io.github.antonbutov"
version = "2.6.0"

repositories {
  mavenCentral()
}

mavenPublishing {
  coordinates(
    groupId = project.group.toString(),
    artifactId = project.name,
    version = project.version.toString(),
  )

  publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
  if (project.findProperty("signing") == "true") {
    signAllPublications()
  }

  pom {
    name.set("Compilation.")
    description.set("Testing tools.")
    url.set("https://github.com/AntonButov/compilation")
    licenses {
      license {
        name.set("The Apache License, Version 2.0")
        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
      }
    }
    developers {
      developer {
        id.set("antonbutov")
        name.set("Anton Butov")
        email.set("butov6101@gmail.com")
      }
    }
    scm {
      connection.set("scm:git:git://github.com/AntonButov/compilation.git")
      developerConnection.set("scm:git:ssh://github.com/AntonButov/compilation.git")
      url.set("https://github.com/AntonButov/compilation")
    }
  }
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(libs.tschuchortdev.testing.ksp)

  testImplementation(kotlin("test"))
  testImplementation(libs.kotest.assertions)
  testImplementation(libs.mockk)
  testImplementation(libs.kotest.runner.junit5.jvm)
}

tasks.test {
  useJUnitPlatform()
}
tasks.named("build") {
  dependsOn("ktlintFormat")
}

tasks.named("test") {
  dependsOn("ktlintFormat")
}

ktlint {
  debug.set(false)
  android.set(false)
  ignoreFailures.set(true)
  reporters {
    reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN)
  }
}

kotlin {
  jvmToolchain(17)
}
