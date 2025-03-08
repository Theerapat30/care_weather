package com.trp.care_weather.core.data.model

import com.trp.core_utils.DateUtils
import com.trp.core_utils.MessageUtils
import java.time.LocalDateTime
import java.time.LocalTime

data class Weather (
    val date: LocalDateTime = LocalDateTime.now(),
    val locationName: String,
    val temp: Temp,
    val tempForecastList: List<Temp>,
    val mainWeather: String,
    val weatherDesc: String,
    val wind: Wind,
//    val rain: Rain,
    val clouds: Clouds,
    val airPollution: AirPollution,
){
    val dateRepresent get() = "${MessageUtils.makeWord(date.dayOfWeek.name)}, ${date.dayOfMonth} ${MessageUtils.makeWord(date.month.name)}"

    fun forecastWeathersFromCurrentTime(): List<Temp>{
        val now = LocalTime.now()
        return tempForecastList.filter { item ->
            DateUtils.isTimeAfterHour(hour = 3, time = now, specificTime = item.localTime)
        }
    }
}

data class Temp(
    val temp: Double,
    val tempFeels: Double,
    val tempMax: Double,
    val tempMin: Double,
    val dateTime: Long = DateUtils.dateToUtc(LocalDateTime.now())
//    val dateTime: LocalDateTime = LocalDateTime.now()
){
    val tempDisplay get() = "$temp  ํ"
    val tempFeelsDisplay get() = "$tempFeels  ํ"
    val maxTempDisplay get() = "$tempMax  ํ"
    val minTempDisplay get() = "$tempMin  ํ"

    fun dayMonthDisplay(): String {
        val dateTime = DateUtils.utcToDate(dateTime)
        return "${dateTime.dayOfMonth}/${dateTime.monthValue}"
    }

    fun time(): String {
        val dateTime = DateUtils.utcToDate(dateTime)
        return "${dateTime.toLocalTime()}"
    }

    val localTime: LocalTime get() = DateUtils.utcToDate(dateTime).toLocalTime()
}

data class AirPollution(
    val aqi: AqiStatus,
    val carbon: Double,
    val ozone: Double,
    val pm25: Double,
    val pm10: Double
){
    val aqiDisplay get() = "AQI ${aqi.name}"
    val carbonDisplay get() = carbon.toString()
    val ozoneDisplay get() = ozone.toString()
    val pm25Display get() = "$pm25 uq\\m3"
    val pm10Display get() = "$pm10 uq\\m3"
}

data class Wind(
    val speed: Double,
    val degree: Double
)

data class Rain(
    val hour: Double
)

data class Clouds(
    val all: Double
)

enum class AqiStatus{
    NA,
    Good,
    Fair,
    Moderate,
    Poor,
    VeryPoor
}

enum class MeasurementUnits{
    Kelvin,
    Celsius,
    Fahrenheit
}