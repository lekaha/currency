const val appVersionName = "1.0.0"
const val appVersionCode = 1
const val appId = "io.github.lekaha.currency"

object Plugins {
    object Versions {
        const val androidGradlePlugin = "3.5.1"
    }

    // Classpath
    const val androidGradlePlugin = "com.android.tools.build:gradle:${Versions.androidGradlePlugin}"
    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"

    // Plugins
    const val androidApplication = "com.android.application"
    const val kotlinAndroid = "android"
    const val kotlinAndroidExtensions = "android.extensions"
    const val kotlinApt = "kapt"
    const val androidDynamicFeature = "com.android.dynamic-feature"
    const val androidLibrary = "com.android.library"
    const val safeargs = "androidx.navigation.safeargs.kotlin"
    const val sqldelight = "com.squareup.sqldelight"
}

object Module {
    // Built-in library
    const val common = ":common"
    const val app = ":app"
}

object Android {
    const val min = 23
    const val compile = 29
    const val target = compile
    const val testRunner = "androidx.test.runner.AndroidJUnitRunner"
}

object BuildTypes {
    const val DEBUG = "debug"
    const val RELEASE = "release"

    const val releaseBuildDebuggable = false
    const val releaseBuildMinify = true
}
