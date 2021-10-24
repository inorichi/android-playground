package com.github.inorichi.marvel.remote

import io.kotest.core.test.TestContext

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
fun TestContext.mockApiResponseFromFile(resourcePath: String): String {
  return javaClass.classLoader.getResourceAsStream(resourcePath).bufferedReader().use {
    it.readText()
  }
}
