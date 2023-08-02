import java.io.FileInputStream
import java.util.Properties

val localProperties = Properties()
localProperties.load(FileInputStream(rootProject.file("local.properties")))

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
   // id 'kotlin-android-extensions'
    id ("kotlin-kapt")
    //SafeArgs
    id ("kotlin-android")
    id ("androidx.navigation.safeargs")
}

android {
    namespace = "com.neotica.submissiondicodingawal"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.neotica.submissiondicodingawal"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            buildConfigField("String", "AUTH_TOKEN", "\"${localProperties["auth.token"]}\"")
        }
        release {
            isMinifyEnabled = false
            buildConfigField("String", "AUTH_TOKEN", "\"${localProperties["auth.token"]}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation ("androidx.core:core-ktx:1.7.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("com.google.android.material:material:1.8.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    //Testing
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.14.2")

    //Retrofit
    implementation ("com.github.bumptech.glide:glide:4.11.0")
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")

    //Navigation
    implementation ("androidx.navigation:navigation-fragment-ktx:2.5.3")
    implementation ("androidx.navigation:navigation-ui-ktx:2.5.3")

    //room
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0")
    implementation ("androidx.room:room-runtime:2.5.0-alpha02")
    kapt ("androidx.room:room-compiler:2.5.0-alpha02")

    //livedata
    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.5.0")


    //DataStore
    implementation ("androidx.datastore:datastore-preferences:1.0.0")

    //koin
    implementation ("io.insert-koin:koin-core:3.2.0")
    implementation ("io.insert-koin:koin-android:3.2.0")

    //coroutine
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
    api ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
}