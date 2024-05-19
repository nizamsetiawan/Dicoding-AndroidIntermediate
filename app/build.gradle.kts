plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.storysnap"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.storysnap"
        minSdk = 29
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
        viewBinding = true
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
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
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity)
    implementation(libs.play.services.maps)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    //splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")
    //retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.11.0")
    implementation ("com.google.code.gson:gson:2.10.1")
    //OKHTTPClient
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    //lifecycle
    val lifecycle_version = "2.7.0"
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycle_version")
    //Gson
    implementation ("com.google.code.gson:gson:2.10.1")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //dataStore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    //activity ktx
    implementation("androidx.activity:activity-ktx:1.9.0")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
    //camera
    implementation("androidx.camera:camera-camera2:1.3.3")
    implementation("androidx.camera:camera-lifecycle:1.3.3")
    implementation("androidx.camera:camera-view:1.3.3")
    //others
    implementation("androidx.exifinterface:exifinterface:1.3.6")
    implementation("com.github.bumptech.glide:glide:4.11.0")
    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    //alert
    implementation ("com.github.f0ris.sweetalert:library:1.6.2")
    //uploudimage
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.makeramen:roundedimageview:2.3.0")
    //map
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.libraries.places:places:3.4.0")
    //unit testing
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("org.mockito:mockito-core:3.12.4")
    testImplementation("org.mockito:mockito-inline:3.12.4")
    //pagging
    implementation ("androidx.paging:paging-runtime-ktx:3.2.1")


}