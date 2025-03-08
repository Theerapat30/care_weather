package com.trp.care_weather.core_network.open_weather

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class OpenWeatherApiTest {

    lateinit var lat: String
    lateinit var lon: String
    lateinit var key: String

    private var openWeatherApi: OpenWeatherApi? = null

    @Before
    fun setupComponent(){
        lat = "13.717457"
        lon = "100.66"
        key = "69a4ded70025da0552029cfce527d0c4"
    }

    @Before
    fun setupApi(){
        val OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"

        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder().addInterceptor(interceptor);
        val okHttpClient = client.build()

        val json = Json { ignoreUnknownKeys = true }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .client(okHttpClient)
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .build()

        openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    }

    @Test
    fun getCurrentWeather_correctLocation_shouldReturnData() = runTest{
        val model = openWeatherApi!!.getCurrentWeather(latitude = lat, longitude = lon, key = key)
        println(model)
        Assert.assertNotNull(model)
    }

}