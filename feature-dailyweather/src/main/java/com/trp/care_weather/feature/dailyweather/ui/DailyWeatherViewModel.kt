package com.trp.care_weather.feature.dailyweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import com.trp.care_weather.core.data.model.AirPollution
import com.trp.care_weather.core.data.model.DailyWeather
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core_domain.GetDailyWeatherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class DailyWeatherViewModel @Inject constructor(
    private val getDailyWeatherUseCase: GetDailyWeatherUseCase
) : ViewModel() {

    val uiState = MutableStateFlow(DailyWeatherUiState())

    init {
        uiState.update {
            it.copy(isFetchingWeather = true)
        }
    }

    fun fetchWeather(latitude: Double, longitude: Double){
        uiState.update { it.copy(isFetchingWeather = true) }
        viewModelScope.launch {
            val dailyWeather: DailyWeather = getDailyWeatherUseCase.invoke(latitude = latitude, longitude = longitude)
            uiState.update {
                it.copy(
                    isFetchingWeather = false,
                    dailyWeather = dailyWeather,
                )
            }
        }
    }

}

data class DailyWeatherUiState(
    val isFetchingWeather: Boolean = true,
    val isError: Boolean = false,
    val dailyWeather: DailyWeather? = null,
    val errorMessage: String? = null
)
