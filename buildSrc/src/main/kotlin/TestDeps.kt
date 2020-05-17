import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

object TestLibraries {
    private object Versions {
        const val junit4 = "4.13"
        const val testRunner = "1.1.1"
        const val espresso = "3.2.0"
        const val mockk = "1.9.3"
        const val mockitoKotlin = "2.2.0"
        const val ktCoroutineCore = "1.3.2"
        const val kluent = "1.56"
        const val sqldelight = "1.2.0"
        const val room = "2.2.5"
    }

    const val junit4 = "junit:junit:${Versions.junit4}"
    const val testRunner = "androidx.test.ext:junit:${Versions.testRunner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockkAndroid = "io.mockk:mockk-android:${Versions.mockk}"
    const val kotlinCoroutineTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.ktCoroutineCore}"
    // Should be changed to mockk
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val kluent = "org.amshove.kluent:kluent-android:${Versions.kluent}"
    const val sqldelight = "com.squareup.sqldelight:sqlite-driver:${Versions.sqldelight}"
    const val room = "androidx.room:room-testing:${Versions.room}"
}

fun DependencyHandler.addTestDependencies() {
    testImplementation(TestLibraries.junit4)
    testImplementation(TestLibraries.mockitoKotlin)
    testImplementation(TestLibraries.mockk)
    testImplementation(TestLibraries.kotlinCoroutineTest)
    testImplementation(TestLibraries.kluent)
    androidTestImplementation(TestLibraries.testRunner)
    androidTestImplementation(TestLibraries.espresso)
    androidTestImplementation(TestLibraries.mockkAndroid)
    androidTestImplementation(TestLibraries.room)
}

fun DependencyHandler.addTestFeatureDependencies() {
    testImplementation(TestLibraries.sqldelight)
}

private fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

private fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)
