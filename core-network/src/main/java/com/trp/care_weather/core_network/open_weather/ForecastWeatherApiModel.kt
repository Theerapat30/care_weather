package com.trp.care_weather.core_network.open_weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastWeatherApiModel(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastListItemApiModel>,
    @SerialName("city")
    val cityApiModel: CityApiModel
)

@Serializable
data class ForecastListItemApiModel(
    val dt: Long,
    val main: TempMainApiModel,
    @SerialName("weather")
    val weatherItemApiModel: List<WeatherItemApiModel>,
    @SerialName("clouds")
    val cloudsApiModel: CloudsApiModel,
    @SerialName("wind")
    val windApiModel: WindApiModel,
    val visibility: Int,
    val pop: Double,
    @SerialName("dt_txt")
    val dtText: String,
)

@Serializable
data class CityApiModel(
    val id: Int,
    val name: String,
    val coord: CoordinateApiModel,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)