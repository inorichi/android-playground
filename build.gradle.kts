buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath(Deps.Android.plugin)
    classpath(Deps.Kotlin.plugin)
    classpath(Deps.Kotlin.Serialization.plugin)
    classpath(Deps.Hilt.plugin)
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
