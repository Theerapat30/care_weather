package com.trp.care_weather.core_domain

import com.trp.care_weather.core.data.AirPollutionRepository
import com.trp.care_weather.core.data.CurrentWeatherRepository
import com.trp.care_weather.core.data.ForecastWeatherRepository
import com.trp.care_weather.core.data.Result
import com.trp.care_weather.core.data.model.AirPollution
import com.trp.care_weather.core.data.model.DailyWeather
import com.trp.care_weather.core.data.model.Temp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface GetDailyWeatherUseCase {
    suspend fun invoke(latitude: Double, longitude: Double): DailyWeather
}

class GetDailyWeatherNetworkUseCase @Inject constructor(
    private val currentWeatherRepository: CurrentWeatherRepository,
    private val forecastWeatherRepository: ForecastWeatherRepository,
    private val airPollutionRepository: AirPollutionRepository,
): GetDailyWeatherUseCase {

    override suspend fun invoke(latitude: Double, longitude: Double): DailyWeather = withContext(Dispatchers.Default){
        var currentWeather: Temp? = null
        val currentWeatherResult = currentWeatherRepository.getWeatherByLocation(latitude = latitude, longitude = longitude)
        if (currentWeatherResult is Result.Success){
            currentWeather = currentWeatherResult.data
        }

        var forecastWeatherList: List<Temp>? = null
        val forecastWeatherResult = forecastWeatherRepository.getWeathers(latitude = latitude, longitude = longitude)
        if (forecastWeatherResult is Result.Success){
            forecastWeatherList = forecastWeatherResult.data
        }

        var airPollution: AirPollution? = null
        val airPollutionResult = airPollutionRepository.getAirPollution(latitude = latitude, longitude = longitude)
        if (airPollutionResult is Result.Success){
            airPollution = airPollutionResult.data
        }

        DailyWeather(
            temp = currentWeather,
            tempForecastList = forecastWeatherList,
            airPollution = airPollution
        )
    }

}