import java.io.FileInputStream
import java.util.Date
import java.util.Properties

val keystorePropertiesFile = rootProject.file("android/keystore.properties")
val keystoreProperties = Properties()
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

val buildTime = Date().time

plugins {
    kotlin("android")
    id("com.android.application")
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.jetbrainsComposeCompiler)
    alias(libs.plugins.google.services)
    alias(libs.plugins.crashlytics)
}

val versionMajor = libs.versions.versionMajor.get().toInt()
val versionMinor = libs.versions.versionMinor.get().toInt()
val versionPatch = libs.versions.versionPatch.get().toInt()
val minSdkValue = libs.versions.android.minSdk.get().toInt()
val targetSdkValue = libs.versions.android.targetSdk.get().toInt()

fun generateVersionCode(): Int {
    return minSdkValue * 10000000 + versionMajor * 10000 + versionMinor * 100 + versionPatch
}

fun generateVersionName(): String {
    val versionName = "${versionMajor}.${versionMinor}.${versionPatch}"
    return versionName
}

android {
    namespace = "eu.bazarsearch.app"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/main/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/main/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "eu.bazarsearch.app"
        minSdk = minSdkValue
        targetSdk = targetSdkValue
        versionCode = generateVersionCode()
        versionName = generateVersionName()

        buildFeatures.buildConfig = true
    }

    signingConfigs {
        getByName("debug") {
        }
        create("release") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }
    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/LICENSE.txt",
                "META-INF/NOTICE.txt",
                "META-INF/README.md",
                "README.md",
                "META-INF/services/javax.annotation.processing.Processor",
                "META-INF/rxjava.properties",
                "META-INF/atomicfu.kotlin_module"
            )
        }
    }
    buildTypes {

        release {

            // we're providing build time for release apks
            buildConfigField("long", "BUILD_TIME", "${buildTime}l")

            buildConfigField("boolean", "DEBUG_MODE_ENABLED", "false")
            buildConfigField("boolean", "CRASH_REPORTING_ENABLED", "true")

            isMinifyEnabled = true
            isShrinkResources = true
            isDebuggable = false

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), // you should use “-optimize” in release block only.
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")
            manifestPlaceholders["enableCrashReporting"] = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    dependencies {
        implementation(project(":shared"))
        implementation(libs.androidx.activity.compose)
        implementation(libs.androidx.core.splashscreen)
        implementation(libs.koin.android)
        debugImplementation(compose.uiTooling)
        implementation(libs.kotlinx.coroutines.android)
        implementation(platform(libs.firebase.android.bom))
        implementation(libs.firebase.android.crashlytics.ktx)
        implementation(libs.firebase.android.analytics)
        debugImplementation(libs.leakcanary.android)
    }
}

