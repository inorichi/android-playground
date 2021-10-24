plugins {
  kotlin("android")
  kotlin("kapt")
  id("com.android.application")
  id("kotlin-android")
  id("dagger.hilt.android.plugin")
}

android {
  compileSdk = Config.compileSdk
  defaultConfig {
    minSdk = Config.minSdk
    targetSdk = Config.targetSdk
    versionCode = Config.versionCode
    versionName = Config.versionName
    applicationId = Config.applicationId
  }

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

dependencies {
  implementation(project(":presentation"))
  implementation(project(":data"))
  implementation(project(":domain"))

  implementation(Deps.Kotlin.stdlib)

  implementation(Deps.Android.coreKtx)
  implementation(Deps.Android.appCompat)

  implementation(Deps.Compose.material)
  implementation(Deps.Compose.ui)
  implementation(Deps.Compose.activity)
  implementation(Deps.Compose.preview)

  implementation(Deps.Hilt.android)
  kapt(Deps.Hilt.compiler)
}

kapt {
  correctErrorTypes = true
}
