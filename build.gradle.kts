buildscript {
  repositories {
    google()
    mavenCentral()
    maven { setUrl("https://s01.oss.sonatype.org/content/repositories/snapshots") }
  }
  dependencies {
    classpath(Deps.Android.plugin)
    classpath(Deps.Kotlin.plugin)
    classpath(Deps.Kotlin.Serialization.plugin)
    classpath(Deps.Hilt.plugin)
    classpath(Deps.Jacoco.plugin)
  }
}

allprojects {
  repositories {
    mavenCentral()
    google()
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}

// Specifying the jacoco version on the jacoco extension doesn't work, so force resolution for all
// the projects applying the plugin
allprojects {
  configurations.all {
    resolutionStrategy.eachDependency {
      if (requested.group == "org.jacoco") {
        useVersion(Deps.Jacoco.toolVersion)
      }
    }
  }
}
