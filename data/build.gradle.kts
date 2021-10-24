import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
  kotlin("android")
  kotlin("kapt")
  kotlin("plugin.serialization")
  id("com.android.library")
  id("dagger.hilt.android.plugin")
}

android {
  compileSdk = Config.compileSdk
  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  defaultConfig {
    val localProperties = gradleLocalProperties(rootDir)

    buildConfigField("String", "API_BASE_URL", "\"https://gateway.marvel.com\"")
    buildConfigField("String", "API_TS", "\"${localProperties.getProperty("api.ts", "")}\"")
    buildConfigField("String", "API_KEY", "\"${localProperties.getProperty("api.key", "")}\"")
    buildConfigField("String", "API_HASH", "\"${localProperties.getProperty("api.hash", "")}\"")
  }
}

kotlin {
  sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
  }
}

dependencies {
  implementation(project(":domain"))

  api(Deps.OkHttp.core)
  api(Deps.Hilt.android)
  api(Deps.Kotlin.Serialization.json)
  api(Deps.Retrofit.core)
  api(Deps.Room.core)

  kapt(Deps.Hilt.compiler)
  kapt(Deps.Room.compiler)

  testImplementation(Deps.Kotest.framework)
  testImplementation(Deps.Kotest.assertions)
  testImplementation(Deps.mockk)
  testImplementation(Deps.OkHttp.mock)
}

kapt {
  correctErrorTypes = true
}

tasks.withType<Test> {
  useJUnitPlatform()
}
