import org.jetbrains.compose.desktop.application.dsl.TargetFormat.*
import java.util.*

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.tecknobit"
version = "1.0.2"

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "18"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("com.github.N7ghtm4r3:Glider:1.0.5")
                implementation("org.json:json:20230227")
                implementation("com.github.N7ghtm4r3:APIManager:2.1.8")
                api(compose.foundation)
                api(compose.animation)
                api(compose.materialIconsExtended)
                api("moe.tlaster:precompose:1.3.14")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "LauncherKt"
        nativeDistributions {
            targetFormats(Deb, Rpm, Pkg, Exe)
            packageName = "Glider"
            packageVersion = "${rootProject.version}"
            version = "${rootProject.version}"
            description = "Glider, open source password manager"
            copyright = "© 2023 Tecknobit."
            vendor = "Tecknobit"
            licenseFile.set(project.file("LICENSE"))
            macOS {
                bundleID = "com.tecknobit.glider"
                iconFile.set(project.file("icons/logo.icns"))
            }
            windows {
                iconFile.set(project.file("icons/logo.ico"))
                upgradeUuid = UUID.randomUUID().toString()
            }
            linux {
                iconFile.set(project.file("icons/logo.png"))
                packageName = "com-tecknobit-glider"
                debMaintainer = "infotecknobitcompany@gmail.com"
                appRelease = "1.0.2"
                appCategory = "PERSONALIZATION"
                rpmLicenseType = "MIT"
            }
        }
        buildTypes.release.proguard {
            configurationFiles.from(project.file("compose-desktop.pro"))
            obfuscate.set(true)
        }
    }
}
