plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.yazstoremlbb"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.yazstoremlbb"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        viewBinding = true
    }
}

dependencies {
    // Dependensi AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // =================================================================
    //         STRUKTUR FIREBASE DEPENDENCY YANG BENAR
    // =================================================================

    // 1. Firebase BOM (Bill of Materials) untuk mengelola versi
    implementation(platform("com.google.firebase:firebase-bom:33.1.2"))

    // 2. Deklarasikan library yang Anda butuhkan TANPA versi
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Baris yang menyebabkan konflik dan HARUS DIHAPUS
    // implementation(libs.firebase.firestore:26.0.2)  <-- PASTIKAN BARIS INI (atau yang sejenis) DIHAPUS

    // =================================================================

    // Dependensi untuk testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

