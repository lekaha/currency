const val kotlinVersion = "1.3.61"

object LintLibraries {
    object Versions {
        const val buildDetekt = "1.5.0"
        const val updaterLibrary = "0.27.0"
    }

    const val detekt = "io.gitlab.arturbosch.detekt"
    const val updater = "com.github.ben-manes.versions"
}

object Libraries {
    private object Versions {
        const val timber = "4.7.1"
        const val jetpack = "1.0.0"
        const val constraintLayout = "1.1.3"
        const val coreKtx = "1.2.0-rc01"
        const val activity = "1.1.0"
        const val fragment = "1.2.0"
        const val ktCoroutineCore = "1.3.3"
        const val play = "1.6.4"
        const val koin = "2.0.1"
        const val koinViewmodel = "2.0.1"
        const val lifecycle = "2.2.0"
        const val coil = "0.9.3"
        const val ktor = "1.2.5"
        const val mtrlDesign = "1.2.0-alpha04"
        const val navigation = "2.3.0-alpha04"
        const val loggingInterceptor = "4.3.1"
        const val urlconnection = "4.3.1"
        const val sqldelight = "1.2.0"
        const val auto = "1.0-rc6"
        const val playPublisher = "2.5.0"
        const val googleService = "4.3.3"
        const val fabric = "1.31.2"
        const val workManager = "2.3.4"
        const val room = "2.2.5"
    }

    // External dependencies
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    const val kotlinReflection = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    const val ktCoroutineCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.ktCoroutineCore}"
    const val ktCoroutineAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.ktCoroutineCore}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    const val playCore = "com.google.android.play:core:${Versions.play}"
    const val ktxCore = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val koin = "org.koin:koin-android:${Versions.koin}"
    const val koinViewmodel = "org.koin:koin-androidx-viewmodel:${Versions.koinViewmodel}"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val lifecycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:${Versions.lifecycle}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val ktor = "io.ktor:ktor-client-okhttp:${Versions.ktor}"
    const val ktorGson = "io.ktor:ktor-client-gson:${Versions.ktor}"
    const val ktorMock = "io.ktor:ktor-client-mock-jvm:${Versions.ktor}"
    const val mtrlDesign = "com.google.android.material:material:${Versions.mtrlDesign}"
    const val naviCommonKtx = "androidx.navigation:navigation-common-ktx:${Versions.navigation}"
    const val naviFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val naviUiKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val naviDynamic = "androidx.navigation:navigation-dynamic-features-fragment:${Versions.navigation}"
    const val sqldelightAndroid = "com.squareup.sqldelight:android-driver:${Versions.sqldelight}"
    const val auto = "com.google.auto.service:auto-service:${Versions.auto}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    const val urlconnection = "com.squareup.okhttp3:okhttp-urlconnection:${Versions.urlconnection}"
    const val workManager = "androidx.work:work-runtime-ktx:${Versions.workManager}"
    const val room = "androidx.room:room-runtime:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"


    // Classpath
    const val navigation = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val sqldelight = "com.squareup.sqldelight:gradle-plugin:${Versions.sqldelight}"
    const val playPublisher = "com.github.triplet.gradle:play-publisher:${Versions.playPublisher}"
    const val googleService = "com.google.gms:google-services:${Versions.googleService}"
}
