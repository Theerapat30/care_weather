package com.trp.care_weather.core.data

import android.net.http.HttpException
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core_network.di.OpenWeatherApiKey
import com.trp.care_weather.core_network.open_weather.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ForecastWeatherRepository {

    suspend fun getWeathers(latitude: Double, longitude: Double): Result<List<Temp>>

}

class ForecastWeatherNetworkRepository @Inject constructor(
    private val openWeatherApi: OpenWeatherApi,
    @OpenWeatherApiKey private val openWeatherApiKey: String
): ForecastWeatherRepository{

    override suspend fun getWeathers(latitude: Double, longitude: Double): Result<List<Temp>> {
        return withContext(Dispatchers.IO){
            try {
                val forecastTempList = openWeatherApi
                    .getForecastWeather(latitude = latitude.toString(), longitude = longitude.toString(), key = openWeatherApiKey)
                    .list.map { item ->
                        Temp(
                            temp = item.main.temp,
                            tempFeels = item.main.feelsLike,
                            tempMax = item.main.tempMax,
                            tempMin = item.main.tempMin,
                            dateTime = item.dt,
                        )
                    }
                Result.Success(forecastTempList)
            } catch (e: HttpException){
                Result.Error(e)
            }
        }
    }

}