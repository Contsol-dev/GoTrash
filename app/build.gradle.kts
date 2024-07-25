plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.adinda.gotrash"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.adinda.gotrash"
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
        viewBinding = true
        buildConfig = true
    }
    packagingOptions {
        exclude("META-INF/LICENSE-notice.md")
        exclude("META-INF/LICENSE.md")
    }
    flavorDimensions += "env"
    productFlavors {
        create("production") {
            buildConfigField(
                type = "String",
                name ="BASE_URL",
                value = "\"http://192.168.1.7:3000/\""
            )
        }
        create("integration") {
            buildConfigField(
                type = "String",
                name ="BASE_URL",
                value = "\"http://192.168.1.7:3000/\""
            )
        }
    }

}

dependencies {
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx.v243)
    implementation(libs.mpandroidchart)
    implementation(libs.android.maps.utils)
    implementation(libs.android.maps.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.coil)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.runtime)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.fragment.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.room.ktx)
    implementation(libs.androidx.annotation)
    implementation(libs.firebase.auth)
    implementation(libs.play.services.maps)
    implementation(libs.firebase.database)
    implementation(libs.play.services.location)
    implementation(libs.firebase.messaging)
    ksp(libs.room.compiler)
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)
    implementation(libs.okhttp)
    implementation(libs.koin.android)
    implementation(libs.shimmer)
    implementation(libs.lottiefiles)
    implementation(platform(libs.firebase.bom))
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.mockk.agent)
    androidTestImplementation(libs.mockk.android)
    testImplementation(libs.coroutine.test)
    testImplementation(libs.turbine)
    testImplementation(libs.core.testing)
}