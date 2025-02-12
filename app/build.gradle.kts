plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.c196mobiledevelopment"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.c196mobiledevelopment"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    val room_version = "2.6.1"

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.room.common)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Room components
    implementation("androidx.room:room-runtime:$rootProject.roomVersion")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    androidTestImplementation("androidx.room:room-testing:$rootProject.roomVersion")
}