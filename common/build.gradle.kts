import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinAndroidExtensions)
}

android {
    compileSdkVersion(Android.compile)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        minSdkVersion(Android.min)
        testInstrumentationRunner = Android.testRunner
    }
    buildTypes {
        getByName(BuildTypes.RELEASE) {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf("-Xuse-experimental=kotlin.Experimental")
    }
}

dependencies {
    api(Libraries.kotlinStdLib)
    api(Libraries.kotlinReflection)
    api(Libraries.ktCoroutineCore)
    api(Libraries.ktCoroutineAndroid)
    api(Libraries.appCompat)
    api(Libraries.ktxCore)
    api(Libraries.activityKtx)
    api(Libraries.fragmentKtx)
    api(Libraries.constraintLayout)
    api(Libraries.playCore)
    api(Libraries.koin)
    api(Libraries.koinViewmodel)
    api(Libraries.lifecycleExtensions)
    api(Libraries.lifecycleViewModel)
    api(Libraries.lifecycleLiveData)
    api(Libraries.coil)
    api(Libraries.ktor)
    api(Libraries.ktorGson)
    api(Libraries.mtrlDesign)

    addTestDependencies()
}
