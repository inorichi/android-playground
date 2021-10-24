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
  }

  object Android {
    const val coreKtx = "androidx.core:core-ktx:1.6.0"
    const val appCompat = "androidx.appcompat:appcompat:1.4.0-beta01"
    const val plugin = "com.android.tools.build:gradle:7.0.3"
    const val paging = "androidx.paging:paging-common:3.1.0-beta01"
  }

  object Compose {
    const val version = "1.0.4"
    const val ui = "androidx.compose.ui:ui:$version"
    const val material = "androidx.compose.material:material:$version"
    const val preview = "androidx.compose.ui:ui-tooling-preview:$version"
    const val activity = "androidx.activity:activity-compose:1.4.0-rc01"
    const val paging = "androidx.paging:paging-compose:1.0.0-alpha14"
    const val coil = "io.coil-kt:coil-compose:1.4.0"
  }

  object Accompanist {
    private const val version = "0.20.0"
    const val navigation = "com.google.accompanist:accompanist-navigation-animation:$version"
  }

  object Hilt {
    private const val version = "2.38.1"
    const val android = "com.google.dagger:hilt-android:$version"
    const val compiler = "com.google.dagger:hilt-android-compiler:$version"
    const val plugin = "com.google.dagger:hilt-android-gradle-plugin:$version"
    const val compose = "androidx.hilt:hilt-navigation-compose:1.0.0-alpha03"
  }

  const val okhttp = "com.squareup.okhttp3:okhttp:4.9.2"

  object Retrofit {
    private const val version = "2.9.0"
    const val core = "com.squareup.retrofit2:retrofit:2.9.0"
    const val serialization = "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.8.0"
  }

  object Room {
    private const val version = "2.3.0"
    const val core = "androidx.room:room-ktx:$version"
    const val compiler = "androidx.room:room-compiler:$version"
  }

}
