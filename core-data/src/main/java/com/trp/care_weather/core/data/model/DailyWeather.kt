package com.trp.care_weather.core.data.model

import com.trp.core_utils.DateUtils
import java.time.LocalTime

data class DailyWeather (
    val temp: Temp?,
    val tempForecastList: List<Temp>?,
    val airPollution: AirPollution?,
){
    fun forecastWeathersFromCurrentTime(): List<Temp>{
        val now = LocalTime.now()
        return tempForecastList?.filter { item ->
            DateUtils.isTimeAfterHour(hour = 3, time = now, specificTime = item.localTime)
        } ?: listOf()
    }
}