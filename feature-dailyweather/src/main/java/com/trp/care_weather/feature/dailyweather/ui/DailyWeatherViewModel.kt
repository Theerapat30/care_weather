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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.trp.care_weather.core.data.DailyWeatherRepository
import com.trp.care_weather.feature.dailyweather.ui.DailyWeatherUiState.Error
import com.trp.care_weather.feature.dailyweather.ui.DailyWeatherUiState.Loading
import com.trp.care_weather.feature.dailyweather.ui.DailyWeatherUiState.Success
import javax.inject.Inject

@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val dailyWeatherRepository: DailyWeatherRepository
) : ViewModel() {

    val uiState: StateFlow<DailyWeatherUiState> = dailyWeatherRepository
        .dailyWeathers.map<List<String>, DailyWeatherUiState> { Success(data = it) }
        .catch { emit(Error(it)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)

    fun addDailyWeather(name: String) {
        viewModelScope.launch {
            dailyWeatherRepository.add(name)
        }
    }
}

sealed interface DailyWeatherUiState {
    object Loading : DailyWeatherUiState
    data class Error(val throwable: Throwable) : DailyWeatherUiState
    data class Success(val data: List<String>) : DailyWeatherUiState
}
