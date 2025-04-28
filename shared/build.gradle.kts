//import co.touchlab.skie.configuration.annotations.SuppressSkieWarning
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree
import java.io.FileInputStream
import java.util.Properties


// Create a variable called keystorePropertiesFile, and initialize it to your
// keystore.properties file, in the rootProject folder.
val keystorePropertiesFile = rootProject.file("android/keystore.properties")

// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()

// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)

    id("kotlin-parcelize")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.jetbrainsComposeCompiler)
    alias(libs.plugins.skie)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    id("de.jensklingenberg.ktorfit") version "2.5.0"
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.crashlytics) apply false
    alias(libs.plugins.room)
    id("com.github.gmazzo.buildconfig") version "5.5.0"
}

kotlin {

    sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata")
    }

    //TODO enable this instead of just iosX64
    applyDefaultHierarchyTemplate()
    //iosArm64()
    //iosSimulatorArm64()

    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }

        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "shared"
            isStatic = true
            binaryOption("bundleId", "eu.bazarsearch.app.shared")
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.accompanist.permissions)
            implementation(libs.androidx.material3.android)
            implementation(libs.androidx.security.crypto.ktx)
            implementation(libs.androidx.runtime.android)
            implementation(libs.androidx.ui.unit.android)
            implementation(libs.androidx.room.runtime.android)
            implementation(libs.androidx.paging.runtime)
            implementation(libs.androidx.paging.compose)

            implementation(libs.androidx.work.runtime.ktx)
            implementation(libs.androidx.browser)

            implementation(libs.coil.kt.compose)
            implementation(libs.coil.kt.compose.gif)

            implementation(project.dependencies.platform(libs.firebase.android.bom))
            implementation(libs.firebase.database)
            implementation(libs.firebase.android.analytics)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.workmanager)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
            implementation(libs.play.services.location)
            implementation(libs.permissionsui)
            implementation(libs.androidx.runtime.livedata)
            implementation(libs.mockk)
        }
        commonMain.dependencies {
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.runtime)
            implementation(compose.ui)

            implementation(libs.androidx.datastore.preferences.core)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.androidx.navigation)
            implementation(libs.androidx.room.paging)

            implementation(libs.autolinktext)

            implementation(libs.cupertino)
            implementation(libs.coil.kt.compose)
            implementation(libs.coil.kt.compose.gif)

            implementation(libs.gitlive.firebase.crashlytics)
            implementation(libs.gitlive.firebase.messaging)
            implementation(libs.gitlive.firebase.database)
            implementation(libs.gitlive.firebase.common)

            //KOIN
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)


            implementation(libs.kotlin.immutability)
            implementation(libs.kotlin.stdlib)
            implementation(libs.kotlinx.datetime)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.ksoup)

            //KTORFIT
            implementation(libs.ktorfit.lib)
            implementation(libs.ktorfit.converters.response)
            implementation(libs.ktorfit.converters.call)
            implementation(libs.ktorfit.converters.flow)

            //KTOR
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.encoding)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.kotlinx.json)

            implementation(libs.kvault)

            implementation(libs.landscapist.coil3)
            implementation(libs.landscapist.placeholder)
            implementation(libs.landscapist.animation)
            implementation(libs.landscapist.palette)

            implementation(libs.moko.permissions)
            implementation(libs.moko.permissions.compose)

            implementation(libs.materii.pullrefresh)

            implementation(libs.androidx.room.runtime)
            implementation(libs.sqlite.bundled)

            implementation(libs.paging.compose.common)
            implementation(libs.paging.common)

            implementation(libs.tech.constraintlayout.compose.multiplatform)

            implementation(libs.skie.annotations)
            implementation(libs.touchlab.kermit)
            implementation(libs.touchlab.kermit.koin)

        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            //implementation(kotlin("test"))
            @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
            implementation(compose.uiTest)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.turbine)
            implementation(libs.koin.test)
        }

        iosMain.dependencies {
            // Workaround for now, see https://github.com/cashapp/sqldelight/issues/4357#issuecomment-1839905700
            implementation(libs.touchlab.stately.common)
            implementation(libs.ktor.client.darwin)
            implementation(libs.paging.runtime.uikit)
        }
        all {
            languageSettings.optIn("androidx.compose.foundation.ExperimentalFoundationApi")
            languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            languageSettings.optIn("org.koin.core.annotation.KoinExperimentalAPI")
        }
    }
}

val versionMajor = libs.versions.versionMajor.get().toInt()
val versionMinor = libs.versions.versionMinor.get().toInt()
val versionPatch = libs.versions.versionPatch.get().toInt()

fun generateVersionName(): String {
    val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    return versionName
}

buildConfig {
    buildConfigField("VERSION_NAME", generateVersionName())
}

ktorfit {
    //errorCheckingMode = ErrorCheckingMode.ERROR
    generateQualifiedTypeName = true
}

compose.resources {
    generateResClass = always
}


tasks.register("androidDebugBuild") {
    description = "Assembles the debug variants of the shared and Android modules."
    group = "Build"

    dependsOn(":shared:assembleDebug", ":android:assembleDebug")
}

tasks.register("androidRebuild") {
    description = "Cleans and builds the debug variants of the shared and Android modules."
    group = "Build"

    dependsOn(":shared:clean", ":shared:assembleDebug", ":android:clean", ":android:assembleDebug")
}

android {
    namespace = "eu.bazarsearch.app.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    dependencies {
        debugImplementation(libs.androidx.compose.ui.tooling)
        androidTestImplementation(libs.androidx.ui.test.junit4.android)
        debugImplementation(libs.androidx.ui.test.manifest)
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.3.2"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }
}


room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(libs.androidx.paging.compose.android)
    listOf(
        "kspAndroid",
        "kspIosSimulatorArm64",
        "kspIosX64",
        "kspIosArm64"
    ).forEach {
        add(it, libs.androidx.room.compiler)
    }
}

//skie { isEnabled = false }

tasks.matching { it.name == "syncComposeResourcesForIos" }
    .configureEach { enabled = false }

//https://slack-chats.kotlinlang.org/t/23153122/im-working-with-compose-multiplatform-project-for-android-an
tasks.whenTaskAdded {
    if (name.contains("copyRoomSchemasToAndroidTestAssetsDebugAndroidTest")) {
        enabled = false
    }
}