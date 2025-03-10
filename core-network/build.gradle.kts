import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlin.serialization)
}

val localProps = Properties()
val localPropertiesFile = File(rootProject.rootDir, "openweather.properties")
if (localPropertiesFile.exists() && localPropertiesFile.isFile){
    localPropertiesFile.inputStream().use {
        localProps.load(it)
    }
}

android {
    namespace = "com.trp.care_weather.core_network"
    compileSdk = libs.versions.comileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        getByName("release") {
            buildConfigField("String", "API_KEY", localProps.getProperty("API_KEY_PRD"))
        }
        getByName("debug") {
            buildConfigField("String", "API_KEY", localProps.getProperty("API_KEY_DEV"))
        }
    }

    buildFeatures {
        aidl = false
        buildConfig = true
        renderScript = false
        shaders = false
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.core.ktx)

    // Retrofit
    implementation(libs.retrofit.client)
    implementation(libs.retrofit.okhttp.logging)

    // Retrofit Kotlin serialization Converter
    implementation(libs.retrofit.serialization.converter)
    implementation(libs.okhttp3.okhttp)

    // Kotlin serialization
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockito.kotlin)

    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}