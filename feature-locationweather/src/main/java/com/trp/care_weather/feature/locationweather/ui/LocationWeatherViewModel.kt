package com.trp.care_weather.feature.locationweather.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.trp.care_weather.core.data.CurrentWeatherRepository
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core.data.Result
import com.trp.care_weather.core.data.di.weatherDummy
import com.trp.care_weather.core.data.model.DailyWeather
import com.trp.care_weather.core_domain.GetDailyWeatherUseCase
import com.trp.care_weather.feature.locationweather.navigation.LocationWeatherRoute
import com.trp.core_utils.DateUtils
import com.trp.core_utils.MessageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class LocationWeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDailyWeatherUseCase: GetDailyWeatherUseCase,
) : ViewModel() {
    private val TAG = LocationWeatherViewModel::class.java.simpleName

    private val locationWeather = savedStateHandle.toRoute<LocationWeatherRoute>()

    val uiState = MutableStateFlow(LocationWeatherUiState(isLoading = true, dailyWeather = null))

    fun fetchWeatherByLocationName(locationName: String){
        viewModelScope.launch {
            uiState.update { it.copy(isLoading = true) }
            val result = getDailyWeatherUseCase.findByLocationName(locationName)
            if (result == null){
                uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "No data found"
                    )
                }
                return@launch
            }
            uiState.update {
                it.copy(
                    isLoading = false,
                    dailyWeather = result
                )
            }

        }
    }

}

data class LocationWeatherUiState(
    val isLoading: Boolean = false,
    val dailyWeather: DailyWeather?,
    val errorMessage: String? = null,
)