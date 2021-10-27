# Android playground

This is a sample Android application with a strong focus on a clean architecture, automated unit and UI testing and continuous integration.

The application shows a list of Marvel characters using the [Marvel API](https://developer.marvel.com/) with the option to search for characters, and a details page showing the selected character profile.

## Building the project

In order to build this project, an API key needs to be provided in the `local.properties` file using the following scheme:

```
api.ts={YOUR_API_TS}
api.key={YOUR_API_KEY}
api.hash={YOUR_API_HASH}
```

More details for getting an API key can be found [here.](https://developer.marvel.com/account)

## Architecture

The application architecture is built based on [Uncle Bob's Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html), and to enforce a proper separation of concerns and limiting boundaries, the project has been divided into 4 modules:

* `domain`: It contains the entities, repositories interfaces and use cases (interactors) of the application. This module's dependencies are as small as possible, and should never depend on any networking or persistency library.

* `data`: It contains the networking and persistency libraries as well as the repository implementations of the `domain` layer through dependency injection.

* `presentation`: It contains the UI and view models of the application. The view models receive in their constructor the `domain` use cases for fetching or updating the data. Note this module does not add a dependency on the `data` module.

* `app`: It's the top-most module, its only job is to bring together the other modules and provide the Android's `Application` and `Activity` classes.

## Libraries

* [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for background work and observer pattern with `Flow`.

* [OkHttp](https://github.com/square/okhttp) and [Retrofit](https://github.com/square/retrofit) for networking.

* [Kotlin Serialization](https://github.com/Kotlin/kotlinx.serialization) for JSON parsing.

* [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection. This was preferred over other pure Kotlin libraries because Hilt/Dagger has support for javax `@Inject` annotations which alongside the Dagger KAPT processor can create our dependencies factories, saving us from writing this boilerplate code. This is especially useful for view models that need several use cases.

* [Room](https://developer.android.com/training/data-storage/room) for database persistency.

* [View Model](https://developer.android.com/topic/libraries/architecture/viewmodel) for writing our view models.

* [Jetpack Compose](https://developer.android.com/jetpack/compose) for building the UI.

* [Jetpack Compose Navigation](https://developer.android.com/jetpack/compose/navigation) for UI navigation.

* [Accompanist](https://github.com/google/accompanist) for system bars tinting and animated navigation.

* [Paging](https://developer.android.com/topic/libraries/architecture/paging/v3-overview) for paginated listings.

* [Kotest](https://github.com/kotest/kotest) for writing the unit tests.

* [MockK](https://github.com/mockk/mockk) for mocking dependencies on our tests.

* [Robolectric](http://robolectric.org/) for running unit tests that require an Android context.

* [Jetpack Compose Test](https://developer.android.com/jetpack/compose/testing) for UI testing.

* [JaCoCo](https://www.eclemma.org/jacoco/) for code coverage reports.
