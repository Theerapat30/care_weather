package com.trp.care_weather.core.data

import com.trp.care_weather.core.data.model.Weather

interface CurrentWeatherRepository{

    suspend fun getWeather(locationName: String): Result<Weather>
    suspend fun getWeatherByLocation(latitude: Double, longitude: Double): Result<Weather>

}