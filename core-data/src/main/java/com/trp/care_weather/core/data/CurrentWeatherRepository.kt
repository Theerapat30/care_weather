package com.trp.care_weather.core.data

import android.net.http.HttpException
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core_network.di.OpenWeatherApiKey
import com.trp.care_weather.core_network.open_weather.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CurrentWeatherRepository{

    suspend fun getWeather(locationName: String): Result<Temp>
    suspend fun getWeatherByLocation(latitude: Double, longitude: Double): Result<Temp>

}

class CurrentWeatherNetworkRepository @Inject constructor(
    private val openWeatherApi: OpenWeatherApi,
    @OpenWeatherApiKey private val openWeatherApiKey: String
) : CurrentWeatherRepository{

    override suspend fun getWeather(locationName: String): Result<Temp> {
        return withContext(Dispatchers.IO){
            try {
                val weatherApiModel = openWeatherApi.getCurrentWeather(location = locationName, key = openWeatherApiKey)
                Result.Success(Temp(
                    temp = weatherApiModel.tempMainApiModel.temp,
                    tempFeels = weatherApiModel.tempMainApiModel.feelsLike,
                    tempMax = weatherApiModel.tempMainApiModel.tempMax,
                    tempMin = weatherApiModel.tempMainApiModel.tempMin,
                    dateTime = weatherApiModel.dt,
                    locationName = weatherApiModel.name
                ))
            } catch (e: HttpException){
                Result.Error(e)
            }
        }
    }

    override suspend fun getWeatherByLocation(
        latitude: Double,
        longitude: Double
    ): Result<Temp> {
        return withContext(Dispatchers.IO){
            try {
                val weatherApiModel = openWeatherApi.getCurrentWeather(
                    latitude = latitude.toString(),
                    longitude = longitude.toString(),
                    key = openWeatherApiKey
                )
                Result.Success(Temp(
                    temp = weatherApiModel.tempMainApiModel.temp,
                    tempFeels = weatherApiModel.tempMainApiModel.feelsLike,
                    tempMax = weatherApiModel.tempMainApiModel.tempMax,
                    tempMin = weatherApiModel.tempMainApiModel.tempMin,
                    dateTime = weatherApiModel.dt,
                    locationName = weatherApiModel.name
                ))
            } catch (e: HttpException){
                Result.Error(e)
            }
        }
    }

}