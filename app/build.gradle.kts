import java.util.Properties
import com.android.build.gradle.internal.dsl.BuildType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinAndroidExtensions)
    kotlin(Plugins.kotlinApt)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.reader())
}

android {
    compileSdkVersion(Android.compile)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    defaultConfig {
        applicationId = appId
        versionCode = appVersionCode
        versionName = appVersionName

        minSdkVersion(Android.min)
        targetSdkVersion(Android.target)
        testInstrumentationRunner = Android.testRunner

        resValue("string", "preference_file_key", "$appId.PREFERENCE_FILE_KEY")
    }

    signingConfigs {
        getByName(BuildTypes.DEBUG) {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        register(BuildTypes.RELEASE) {
            storeFile = rootProject.file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        getByName(BuildTypes.DEBUG) {
            isDebuggable = true
            matchingFallbacks = listOf(BuildTypes.DEBUG)
            applicationIdSuffix = ".debug"
            versionNameSuffix = ".debug"
        }
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = BuildTypes.releaseBuildMinify
            signingConfig = signingConfigs.getByName(BuildTypes.RELEASE)
            isDebuggable = BuildTypes.releaseBuildDebuggable
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigFieldFromGradleProperty("accessKey")
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/main.kotlin_module")
        exclude("META-INF/kotlinx-io.kotlin_module")
        exclude("META-INF/atomicfu.kotlin_module")
        exclude("META-INF/kotlinx-coroutines-io.kotlin_module")
        exclude("META-INF/kotlinx-coroutines-core.kotlin_module")
        exclude("lib/arm64-v8a/libc++_shared.so")
        exclude("lib/armeabi-v7a/libc++_shared.so")
        exclude("lib/x86/libc++_shared.so")
        exclude("lib/x86_64/libc++_shared.so")
    }

    dataBinding {
        isEnabled = true
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xuse-experimental=kotlin.Experimental",
            "-XXLanguage:+InlineClasses"
        )
    }
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    api(project(Module.common))
    api(Libraries.kotlinStdLib)
    kapt(Libraries.auto)

    implementation(Libraries.timber)
    implementation(Libraries.appCompat)
    implementation(Libraries.ktxCore)
    implementation(Libraries.constraintLayout)
    implementation(Libraries.lifecycleExtensions)
    implementation(Libraries.lifecycleViewModel)
    implementation(Libraries.lifecycleLiveData)
    implementation(Libraries.workManager)
    implementation(Libraries.koin)
    implementation(Libraries.koinViewmodel)
    implementation(Libraries.loggingInterceptor)
    implementation(Libraries.room)
    kapt(Libraries.roomCompiler)
    implementation(Libraries.roomKtx)

    addTestDependencies()
}

fun BuildType.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue = project.properties[gradlePropertyName] as? String
        ?: localProperties[gradlePropertyName] as? String
        ?: System.getenv("ACCESS_KEY")
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, propertyValue)
}

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") {
    it.toLowerCase()
}