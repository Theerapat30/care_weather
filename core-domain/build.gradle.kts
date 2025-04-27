plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

kotlin {
    jvmToolchain(libs.versions.jvmVersion.get().toInt())
}

android {
    namespace = "com.trp.care_weather.core_domain"
    compileSdk = libs.versions.comileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }


    compileOptions {
        sourceCompatibility = rootProject.extra["sourceCompatibility"] as JavaVersion
        targetCompatibility = rootProject.extra["targetCompatibility"] as JavaVersion
    }

    kotlinOptions {
        jvmTarget = libs.versions.jvmVersion.get()
    }
}

dependencies {
    implementation(project(":core-data"))
    implementation(project(":core-utils"))

    // Arch Components
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit)
}