plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.eibrahim.chatbot"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.eibrahim.chatbot"
        minSdk = 24
        targetSdk = 35
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.preference)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.annotation)
    implementation(libs.androidx.drawerlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.recyclerview)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

// Retrofit & Converters
    implementation(libs.retrofit.v290)
    implementation(libs.converter.gson)
    implementation(libs.converter.moshi)

// OkHttp
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

// AndroidX Core and Navigation
    implementation(libs.androidx.activity.ktx)

// Google Play Services
//    implementation("com.google.android.gms:play-services-auth:20.7.0")

// Material Components


// Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

// Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

// OpenCSV
//    implementation("com.opencsv:opencsv:5.9")

// Glide
    implementation(libs.glide)
    implementation(libs.okhttp3.integration)

// Stripe Payment SDK
    implementation(libs.stripe.android)

// AndroidX Fragment Testing
//    implementation("androidx.fragment:fragment-testing:1.7.0")

// Unit Testing
//    testImplementation("junit:junit:4.13.2")
//    testImplementation("org.mockito:mockito-core:5.7.0")
//    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
//    testImplementation("com.google.truth:truth:1.1.5")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")
//    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
//    testImplementation("org.robolectric:robolectric:4.11.1")
//    testImplementation("io.mockk:mockk:1.13.5")
//    testImplementation("androidx.arch.core:core-testing:2.1.0")
//    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
//    testImplementation("com.google.code.gson:gson:2.11.0")

    implementation(libs.core)


}