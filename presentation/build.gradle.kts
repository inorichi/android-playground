plugins {
  kotlin("android")
  kotlin("kapt")
  id("com.android.library")
  id("dagger.hilt.android.plugin")
  id("com.dicedmelon.gradle.jacoco-android")
}

android {
  compileSdk = Config.compileSdk

  defaultConfig {
    minSdk = Config.minSdk
    targetSdk = Config.targetSdk
    testInstrumentationRunner = "com.github.inorichi.marvel.CustomTestRunner"
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

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Deps.Compose.version
  }

  buildTypes {
    debug {
      isTestCoverageEnabled = true
    }
  }

  packagingOptions {
    resources {
      excludes += "META-INF/AL2.0"
      excludes += "META-INF/LGPL2.1"
    }
  }
}

kotlin {
  sourceSets.all {
    languageSettings {
      optIn("kotlin.RequiresOptIn")
      optIn("androidx.compose.animation.ExperimentalAnimationApi")
    }
  }
}

dependencies {
  implementation(project(":domain"))

  implementation(Deps.Kotlin.stdlib)

  implementation(Deps.Android.coreKtx)
  implementation(Deps.Android.appCompat)

  implementation(Deps.Compose.material)
  implementation(Deps.Compose.ui)
  implementation(Deps.Compose.preview)
  implementation(Deps.Compose.paging)
  implementation(Deps.Compose.coil)
  implementation(Deps.Accompanist.navigation)
  implementation(Deps.Accompanist.systemui)

  implementation(Deps.Hilt.android)
  implementation(Deps.Hilt.compose)
  kapt(Deps.Hilt.compiler)

  androidTestImplementation(Deps.Android.test)
  androidTestImplementation(Deps.Android.testRunner)
  androidTestImplementation(Deps.Mockk.android)
  androidTestImplementation(Deps.Compose.test)
  androidTestImplementation(Deps.Hilt.test)
  androidTestImplementation(project(":data"))
  debugImplementation(Deps.Compose.testManifest)
  kaptAndroidTest(Deps.Hilt.compiler)

  testImplementation(Deps.Kotest.framework)
  testImplementation(Deps.Kotest.assertions)
  testImplementation(Deps.Mockk.jvm)
  testImplementation(Deps.Kotlin.Coroutines.test)
}

kapt {
  correctErrorTypes = true
}

tasks.withType<Test> {
  useJUnitPlatform()
}
