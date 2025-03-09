package com.trp.care_weather.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.trp.care_weather.core.data.AirPollutionNetworkRepository
import com.trp.care_weather.core.data.Result
import com.trp.care_weather.core_network.open_weather.OpenWeatherApi
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class AirPollutionNetworkRepositoryTest {

    private var lat: Double = 0.00
    private var lon: Double = 0.00

    private lateinit var repo: AirPollutionNetworkRepository

    @Before
    fun setup(){
        lat = 13.717457
        lon = 100.66
    }

    @Before
    fun setupNetwork(){
        val OPEN_WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/"

        val json = Json { ignoreUnknownKeys = true }

        val retrofit = Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(OPEN_WEATHER_BASE_URL)
            .build()

        val openWeatherApi = retrofit.create(OpenWeatherApi::class.java)
        repo = AirPollutionNetworkRepository(openWeatherApi)
    }

    @Test
    fun getAirPollution_inputCorrect_shouldReturnData() = runTest {
        val result = repo.getAirPollution(latitude = lat, longitude = lon)
        Assert.assertTrue(result is Result.Success)
        val data = (result as Result.Success).data
        println("result data: $data")
        Assert.assertNotNull(data)
    }

}