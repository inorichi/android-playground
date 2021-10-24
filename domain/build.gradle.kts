plugins {
  kotlin("android")
  id("com.android.library")
}

android {
  compileSdk = 31
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
  testImplementation(Deps.mockk)
}

tasks.withType<Test> {
  useJUnitPlatform()
}
