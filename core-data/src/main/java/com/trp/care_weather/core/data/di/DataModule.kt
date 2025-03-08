/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.trp.care_weather.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import com.trp.care_weather.core.data.DailyWeatherRepository
import com.trp.care_weather.core.data.DefaultDailyWeatherRepository
import com.trp.care_weather.core.data.model.AirPollution
import com.trp.care_weather.core.data.model.AqiStatus
import com.trp.care_weather.core.data.model.Clouds
import com.trp.care_weather.core.data.model.Rain
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core.data.model.Weather
import com.trp.care_weather.core.data.model.Wind
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindsDailyWeatherRepository(
        dailyWeatherRepository: DefaultDailyWeatherRepository
    ): DailyWeatherRepository
}

class FakeDailyWeatherRepository @Inject constructor() : DailyWeatherRepository {
    override val dailyWeathers: Flow<List<String>> = flowOf(fakeDailyWeathers)

    override suspend fun add(name: String) {
        throw NotImplementedError()
    }
}

val fakeDailyWeathers = listOf("One", "Two", "Three")

val tempDummy = Temp(
    temp = 14.5,
    tempFeels = 15.0,
    tempMax = 18.0,
    tempMin = 12.0
)

val windDummy = Wind(
    speed = 4.59,
    degree = 35.0
)

val rainDummy = Rain(
    hour = 20.0
)

val cloudsDummy = Clouds(
    all = 95.0
)

val airPollutionDummy = AirPollution(
    aqi = AqiStatus.Good,
    carbon = 240.33,
    ozone = 54.36,
    pm25 = 18.03,
    pm10 = 54.9
)

val tempForecastDummy = listOf(
    Temp(
        dateTime = 1734166800L,
        temp = 30.1,
        tempFeels = 29.0,
        tempMax = 32.5,
        tempMin = 28.0
    ),
    Temp(
        dateTime = 1734220800L,
        temp = 25.4,
        tempFeels = 24.5,
        tempMax = 25.6,
        tempMin = 21.9,
    ),
    Temp(
        dateTime = 1734307200L,
        temp = 22.4,
        tempFeels = 22.5,
        tempMax = 23.6,
        tempMin = 20.9,
    ),
    Temp(
        dateTime = 1734393600L,
        temp = 22.93,
        tempFeels = 22.85,
        tempMax = 22.93,
        tempMin = 22.93,
    ),
    Temp(
        dateTime = 1734480000L,
        temp = 24.04,
        tempFeels = 23.99,
        tempMax = 24.04,
        tempMin = 24.04,
    ),
)

val weatherDummy = Weather(
    locationName = "Bangkok",
    temp = tempDummy,
    mainWeather = "",
    weatherDesc = "",
    wind = windDummy,
//    rain = rainDummy,
    clouds = cloudsDummy,
    airPollution = airPollutionDummy,
    tempForecastList = tempForecastDummy
)