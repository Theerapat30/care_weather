package com.trp.care_weather.feature.forecastweather.navigation

import kotlinx.serialization.Serializable

@Serializable data class ForecastWeatherRoute(val dateTime: Long, val latitude: Double, val longitude: Double)