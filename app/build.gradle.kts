plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.reysl.videoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.reysl.videoapp"
        minSdk = 26
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Library for testing
    // Local Unit tests
    testImplementation("androidx.test:core:1.6.1")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
    testImplementation(libs.junit)
    testImplementation(libs.hilt.android.testing)
    testImplementation("com.google.truth:truth:1.1.3")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("io.mockk:mockk:1.13.5")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    debugImplementation(libs.androidx.ui.test.manifest)


    // Instrumentation tests
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptTest("com.google.dagger:hilt-android-compiler:2.51.1")


    // ExoPlayer
    implementation("androidx.media3:media3-exoplayer:1.5.1")
    implementation("androidx.media3:media3-exoplayer-dash:1.5.1")
    implementation("androidx.media3:media3-ui:1.5.1")

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.7")

    // Dagger-Hilt
    implementation("com.google.dagger:hilt-android:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    kapt("androidx.hilt:hilt-compiler:1.2.0")

    implementation("androidx.compose.material:material-icons-extended:1.7.8")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // System Ui Controller
    implementation ("com.google.accompanist:accompanist-systemuicontroller:0.36.0")

    implementation("io.coil-kt:coil-compose:2.6.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    // SwipeRefresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.30.1")

    implementation("com.google.code.gson:gson:2.10.1")
}