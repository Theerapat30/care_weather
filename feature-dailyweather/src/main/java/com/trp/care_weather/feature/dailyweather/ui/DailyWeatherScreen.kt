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

package com.trp.care_weather.feature.dailyweather.ui

import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener

@Composable
fun DailyWeatherScreen(
    viewModel: DailyWeatherViewModel = hiltViewModel()
){
    val context = LocalContext.current

    val locationClient: FusedLocationProviderClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Log.d("TAG", "HomeScreen uiState...")

    val locationListener: OnSuccessListener<Location> = OnSuccessListener<Location>{ location ->
        viewModel.fetchWeather(latitude = location.latitude, longitude = location.longitude)
    }

    if (uiState is HomeUiState.HasWeather){
        HomeScreen(
            weather = (uiState as HomeUiState.HasWeather).weather,
            onRefresh = {
                locationClient.lastLocation.addOnSuccessListener(locationListener)
            },
            isRefreshing = uiState.isLoading,
            versionName = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_ACTIVITIES
            ).versionName,
            onNavigateToForecastWeather = onNavigateToForecastWeather
        )
    } else {
        locationClient.lastLocation.addOnSuccessListener(locationListener)
        NoWeatherDataScreen()
    }
}