package com.trp.care_weather.core_network.open_weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherApiModel(
    val coord: CoordinateApiModel,
    @SerialName("weather")
    val weatherItemApiModels: List<WeatherItemApiModel>,
    val base: String,
    @SerialName("main")
    val tempMainApiModel: TempMainApiModel,
    val visibility: Int,
    @SerialName("wind")
    val windApiModel: WindApiModel,
//    val rain: Rain,
    @SerialName("clouds")
    val cloudsApiModel: CloudsApiModel,
    val dt: Long,
//    val sys: Sys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
)

@Serializable
data class CoordinateApiModel(val lon: Double, val lat: Double)

@Serializable
data class WeatherItemApiModel(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

@Serializable
data class TempMainApiModel(
    val temp: Double,
    @SerialName("feels_like")
    val feelsLike: Double,
    @SerialName("temp_min")
    val tempMin: Double,
    @SerialName("temp_max")
    val tempMax: Double,
    @SerialName("pressure")
    val pressure: Int,
    @SerialName("humidity")
    val humidity: Int,
    @SerialName("sea_level")
    val seaLevel: Int,
    @SerialName("grnd_level")
    val groundLevel: Int
)

@Serializable
data class WindApiModel(
    val speed: Double,
    val deg: Int,
)

@Serializable
data class RainApiModel(
    @SerialName("1h")
    val hourly: Double
)

@Serializable
data class CloudsApiModel(
    val all: Int
)

@Serializable
data class SysApiModel(
    val type: Int,
    val id: Int,
)
