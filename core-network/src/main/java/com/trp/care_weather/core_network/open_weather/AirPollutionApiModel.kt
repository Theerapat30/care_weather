package com.trp.care_weather.core_network.open_weather

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirPollutionApiModel(
    val coord: CoordinateApiModel,
    val list: List<AirPollutionListItemApiModel>,
)

@Serializable
data class AirPollutionListItemApiModel(
    val main: AirPollutionMainApiModel,
    val components: AirPollutionComponentApiModel,
    val dt: Long
)

@Serializable
data class AirPollutionMainApiModel(
    val aqi: Int,
)

@Serializable
data class AirPollutionComponentApiModel(
    val co: Double,
    val no: Double,
    val no2: Double,
    val o3: Double,
    val so2: Double,
    @SerialName("pm2_5")
    val pm25: Double,
    val pm10: Double,
    val nh3: Double,
)

