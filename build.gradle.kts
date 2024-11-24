
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
//        classpath libs.hilt.android.gradle.plugin
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    id("com.android.library") version "8.1.1" apply false
}
