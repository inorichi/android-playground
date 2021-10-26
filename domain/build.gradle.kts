plugins {
  kotlin("android")
  id("com.android.library")
  id("com.dicedmelon.gradle.jacoco-android")
}

android {
  compileSdk = Config.compileSdk

  defaultConfig {
    minSdk = Config.minSdk
    targetSdk = Config.targetSdk
  }

  sourceSets {
    named("main") {
      java.srcDir("src/main/kotlin")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }
}

dependencies {
  implementation(Deps.Hilt.android)
  api(Deps.Android.paging)

  testImplementation(Deps.Kotest.framework)
  testImplementation(Deps.Kotest.assertions)
  testImplementation(Deps.Mockk.jvm)
}

tasks.withType<Test> {
  useJUnitPlatform()
}
