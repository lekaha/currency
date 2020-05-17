// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath(Plugins.androidGradlePlugin)
        classpath(Plugins.kotlinGradlePlugin)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

plugins {
    id(LintLibraries.detekt).version(LintLibraries.Versions.buildDetekt)
    id(LintLibraries.updater).version(LintLibraries.Versions.updaterLibrary)
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

detekt {
    toolVersion = LintLibraries.Versions.buildDetekt
    debug = true
    parallel = true
    input = files("src/main/java")
    config = files("$rootDir/detekt.yml")
    baseline = file("$rootDir/baseline.xml")

    idea {
        path = "$rootDir/.idea"
        codeStyleScheme = "$rootDir/.idea/idea-code-style.xml"
        inspectionsProfile = "$rootDir/.idea/inspect.xml"
        mask = "*.kt"
    }

    reports {
        xml {
            enabled = true // Enable/Disable XML report (default: true)
            destination =
                file("build/reports/detekt.xml") // Path where XML report will be stored (default: `build/reports/detekt/detekt.xml`)
        }
        html {
            enabled = true // Enable/Disable HTML report (default: true)
            destination =
                file("build/reports/detekt.html") // Path where HTML report will be stored (default: `build/reports/detekt/detekt.html`)
        }
        txt {
            enabled = true // Enable/Disable TXT report (default: true)
            destination =
                file("build/reports/detekt.txt") // Path where TXT report will be stored (default: `build/reports/detekt/detekt.txt`)
        }
        custom {
            reportId = "CustomJsonReport" // The simple class name of your custom report.
            destination = file("build/reports/detekt.json") // Path where report will be stored
        }
    }
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
    exclude(".*/resources/.*", ".*/build/.*") // but exclude our legacy internal package
}

tasks.register("clean").configure {
    delete("build")
}
