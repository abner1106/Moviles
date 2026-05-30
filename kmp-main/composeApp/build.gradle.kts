import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.ksp)

    alias(libs.plugins.kotlinSerialization)
}

kotlin {

    androidTarget {

        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->

        iosTarget.binaries.framework {

            baseName = "ComposeApp"
            isStatic = true
        }
    }

    js {

        browser()

        binaries.executable()
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {

        browser()

        binaries.executable()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.compose.components.resources)
                implementation(libs.compose.uiToolingPreview)
                implementation(libs.androidx.lifecycle.viewmodelCompose)
                implementation(libs.androidx.lifecycle.runtimeCompose)
                implementation("me.tatarka.inject:kotlin-inject-runtime:0.9.0")
                implementation("org.jetbrains.androidx.navigation:navigation-compose:2.9.2")
                implementation("io.ktor:ktor-client-core:3.4.3")
                implementation("io.ktor:ktor-client-content-negotiation:3.4.3")
                implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.11.0")
                implementation("io.ktor:ktor-client-logging:3.4.3")
                implementation("io.ktor:ktor-client-auth:3.4.3")
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.androidx.activity.compose)
                implementation(libs.compose.uiToolingPreview)
                implementation("io.ktor:ktor-client-okhttp:3.4.3")
            }
        }

        val iosMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:3.4.3")
            }
        }

        val iosArm64Main by getting {
            dependsOn(iosMain)
        }

        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }

        val webMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("io.ktor:ktor-client-js:3.4.3")
            }
        }

        val jsMain by getting {
            dependsOn(webMain)
        }

        val wasmJsMain by getting {
            dependsOn(webMain)
        }

        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {

    namespace = "edu.itvo.kmp1"

    compileSdk =
        libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "edu.itvo.kmp1"
        minSdk =
            libs.versions.android.minSdk.get().toInt()
        targetSdk = 
            libs.versions.android.targetSdk.get().toInt()
    }

    packaging {

        resources {

            excludes +=
                "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {

        getByName("release") {

            isMinifyEnabled = false
        }
    }

    compileOptions {

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {

    debugImplementation(
        libs.compose.uiTooling
    )

    add(
        "kspCommonMainMetadata",
        "me.tatarka.inject:kotlin-inject-compiler-ksp:0.9.0"
    )

    add(
        "kspAndroid",
        "me.tatarka.inject:kotlin-inject-compiler-ksp:0.9.0"
    )
}