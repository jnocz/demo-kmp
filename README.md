Kotlin Multiplatform project targeting Android, iOS.

This repository is DEMO version of the existing application https://play.google.com/store/apps/details?id=eu.bazarsearch.app

For DEMO purposes is online search DISABLED, database is prepopulated with sample search results.

* `/shared` is for code that is shared across Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
* `/android` contains Android application  
* `/ios` contains iOS application.

Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…
