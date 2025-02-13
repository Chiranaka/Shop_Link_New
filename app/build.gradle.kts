plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.shoplink"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.shoplink"
        minSdk = 23
        targetSdk = 34
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
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.google.material)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.camera.core)
    implementation(libs.camera.camera2)
    implementation(libs.zxing.android.embedded)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)
    implementation(libs.play.services.mlkit.barcode.scanning)
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.barcode.scanning)
    implementation(libs.guava)
    implementation(libs.mlkit.barcode.scanning)
    implementation(libs.play.services.code.scanner)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}