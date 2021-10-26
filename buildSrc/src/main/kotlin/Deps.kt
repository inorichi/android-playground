@Suppress("MemberVisibilityCanBePrivate")
object Deps {
  object Kotlin {
    private const val version = "1.5.31"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    const val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"

    object Serialization {
      private const val version = "1.3.0"
      const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:$version"
      const val plugin = "org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"
    }

    object Coroutines {
      private const val version = "1.5.2"
      const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
      const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
    }
  }

  object Android {
    const val coreKtx = "androidx.core:core-ktx:1.6.0"
    const val appCompat = "androidx.appcompat:appcompat:1.4.0-beta01"
    const val plugin = "com.android.tools.build:gradle:7.0.3"
    const val paging = "androidx.paging:paging-common:3.1.0-beta01"
    const val test = "androidx.test:core-ktx:1.4.0"
  }

  object Compose {
    const val version = "1.0.4"
    const val ui = "androidx.compose.ui:ui:$version"
    const val material = "androidx.compose.material:material:$version"
    const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
    const val activity = "androidx.activity:activity-compose:1.4.0-rc01"
    const val paging = "androidx.paging:paging-compose:1.0.0-alpha14"
    const val coil = "io.coil-kt:coil-compose:1.4.0"
    const val test = "androidx.compose.ui:ui-test-junit4:$version"
    const val testManifest = "androidx.compose.ui:ui-test-manifest:$version"
  }

  object Accompanist {
    private const val version = "0.20.0"
    const val navigation = "com.google.accompanist:accompanist-navigation-animation:$version"
    const val systemui = "com.google.accompanist:accompanist-systemuicontroller:$version"
  }

  object Hilt {
    private const val version = "2.38.1"
    const val android = "com.google.dagger:hilt-android:$version"
    const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    const val compose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
  }

  object OkHttp {
    private const val version = "4.9.2"
    const val core = "com.squareup.okhttp3:okhttp:$version"
    const val mock = "com.squareup.okhttp3:mockwebserver:$version"
  }

  object Retrofit {
    private const val version = "2.9.0"
    const val core = "com.squareup.retrofit2:retrofit:2.9.0"
  }

  object Room {
    private const val version = "2.3.0"
    const val core = "androidx.room:room-ktx:$version"
    const val compiler = "androidx.room:room-compiler:$version"
  }

  object Kotest {
    private const val version = "4.6.3"
    const val framework = "io.kotest:kotest-runner-junit5:$version"
    const val assertions = "io.kotest:kotest-assertions-core:$version"
    const val robolectric = "io.kotest.extensions:kotest-extensions-robolectric:0.4.0"
  }

  object Mockk {
    const val version = "1.12.0"
    const val jvm = "io.mockk:mockk:$version"
    const val android = "io.mockk:mockk-android:$version"
  }

  const val robolectric = "org.robolectric:robolectric:4.5.1"

  object Jacoco {
    // Current release does not work with the project, so using the snapshot
    const val plugin = "com.dicedmelon.gradle:jacoco-android:0.1.5-SNAPSHOT"
    const val toolVersion = "0.8.7"
  }

}
