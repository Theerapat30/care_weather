package com.trp.care_weather.core_network.open_weather

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi{
    @GET("weather")
    suspend fun getCurrentWeather(@Query("q") location: String, @Query("APPID") key: String, @Query("units") units: String = "metric"): WeatherApiModel

    @GET("weather")
    suspend fun getCurrentWeather(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("APPID") key: String, @Query("units") units: String = "metric"): WeatherApiModel

    @GET("forecast")
    suspend fun getForecastWeather(@Query("q") location: String, @Query("appid") key: String, @Query("units") units: String = "metric"): ForecastWeatherApiModel

    @GET("forecast")
    suspend fun getForecastWeather(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("appid") key: String, @Query("units") units: String = "metric"): ForecastWeatherApiModel

    @GET("air_pollution")
    suspend fun getCurrentAirPollution(@Query("lat") latitude: String, @Query("lon") longitude: String, @Query("appid") key: String): AirPollutionApiModel
}
