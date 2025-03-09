package com.trp.care_weather.core.data

import android.net.http.HttpException
import com.trp.care_weather.core.data.model.AirPollution
import com.trp.care_weather.core.data.model.AqiStatus
import com.trp.care_weather.core_network.open_weather.AirPollutionApiModel
import com.trp.care_weather.core_network.open_weather.AirPollutionListItemApiModel
import com.trp.care_weather.core_network.open_weather.OpenWeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AirPollutionRepository {

    suspend fun getAirPollution(latitude: Double, longitude: Double): Result<AirPollution>

}

class AirPollutionNetworkRepository @Inject constructor(
    private val openWeatherApi: OpenWeatherApi
) : AirPollutionRepository{

    private val key = "69a4ded70025da0552029cfce527d0c4"

    override suspend  fun getAirPollution(latitude: Double, longitude: Double): Result<AirPollution> {
        return withContext(Dispatchers.IO){
            try {
                val apiModel: AirPollutionApiModel = openWeatherApi.getCurrentAirPollution(latitude = latitude.toString(), longitude = longitude.toString(), key = key)
                val airPollutionItem: AirPollutionListItemApiModel = apiModel.list[0]
                Result.Success(AirPollution(
                    aqi = toAqiStatus(airPollutionItem.main.aqi),
                    carbon = airPollutionItem.components.co,
                    ozone = airPollutionItem.components.o3,
                    pm25 = airPollutionItem.components.pm25,
                    pm10 = airPollutionItem.components.pm10,
                ))
            } catch (e: HttpException){
                Result.Error(e)
            }
        }
    }

    private fun toAqiStatus(aqi: Int) : AqiStatus {
        return when(aqi){
            1 -> AqiStatus.Good
            2 -> AqiStatus.Fair
            3 -> AqiStatus.Moderate
            4 -> AqiStatus.Poor
            5 -> AqiStatus.VeryPoor
            else -> AqiStatus.NA
        }
    }

}