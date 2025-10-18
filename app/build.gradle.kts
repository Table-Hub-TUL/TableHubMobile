plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id("kotlin-parcelize")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "pl.tablehub.mobile"
    compileSdk = 35

    val backendIp: String = project.findProperty("BACKEND_IP") as? String
        ?: project.rootProject.file("local.properties").readLines()
            .find { it.startsWith("BACKEND_IP=") }
            ?.split("=")
            ?.get(1)
            ?.trim()
        ?: throw GradleException("Backend IP not found")

    lint {
        disable.add("NullSafeMutableLiveData")
    }

    defaultConfig {
        applicationId = "pl.tablehub.mobile"
        minSdk = 30
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BACKEND_IP", "\"$backendIp\"")
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(platform(libs.coil.bom))
    implementation(libs.maps.compose)
    implementation(libs.mapbox.maps)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.material.icons.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.krossbow.stomp.core.v930)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.hilt.common)
    implementation(libs.logging.interceptor)
    implementation(libs.krossbow.stomp.core)
    implementation(libs.androidx.datastore)
    implementation(libs.krossbow.websocket.builtin)
    implementation(libs.jackson.databind)
    implementation(libs.krossbow.websocket.okhttp)
    implementation(libs.okhttp)
    implementation(libs.androidx.datastore.preferences.core)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.security.crypto)
    implementation(libs.androidx.appcompat)
    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.play.services.location)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core.android)
    ksp(libs.hilt.android.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}