plugins {
  kotlin("android")
  kotlin("kapt")
  id("com.android.library")
  id("dagger.hilt.android.plugin")
}

android {
  compileSdk = Config.compileSdk

  compileOptions {
    sourceCompatibility(JavaVersion.VERSION_1_8)
    targetCompatibility(JavaVersion.VERSION_1_8)
  }

  buildFeatures {
    compose = true
  }

  composeOptions {
    kotlinCompilerExtensionVersion = Deps.Compose.version
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
  implementation(Deps.Compose.activity)
  implementation(Deps.Compose.preview)
  implementation(Deps.Compose.paging)
  implementation(Deps.Compose.coil)
  implementation(Deps.Accompanist.navigation)

  implementation(Deps.Hilt.android)
  implementation(Deps.Hilt.compose)
  kapt(Deps.Hilt.compiler)
}

kapt {
  correctErrorTypes = true
}
