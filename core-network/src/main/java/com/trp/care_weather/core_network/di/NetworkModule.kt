package com.trp.care_weather.core_network.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.trp.care_weather.core_network.open_weather.OpenWeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi {
        return retrofit.create(OpenWeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideOpenWeather(): Retrofit{
        val OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor);
        val okHttpClient = client.build()

        val json = Json { ignoreUnknownKeys = true }

        return Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .build()
    }

}