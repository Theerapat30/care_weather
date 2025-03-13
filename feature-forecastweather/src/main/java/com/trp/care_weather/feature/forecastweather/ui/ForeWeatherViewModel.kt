package com.trp.care_weather.feature.forecastweather.ui

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.trp.care_weather.core.data.ForecastWeatherRepository
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.feature.forecastweather.navigation.ForecastWeatherRoute
import com.trp.care_weather.core.data.Result
import com.trp.core_utils.DateUtils
import com.trp.core_utils.MessageUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ForecastWeatherViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val forecastRepository: ForecastWeatherRepository,
) : ViewModel() {
    private val TAG = ForecastWeatherViewModel::class.java.simpleName

    private val forecastWeather = savedStateHandle.toRoute<ForecastWeatherRoute>()

    val uiState = MutableStateFlow(ForecastWeatherUiState(isLoading = true))

    private val date = DateUtils.utcToDate(forecastWeather.dateTime)

    init {
        Log.d(TAG, "forecastWeather = $forecastWeather")
        fetchForecastWeather()
    }

    private fun fetchForecastWeather(){
        uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            try{
                val result = forecastRepository.getWeathers(latitude = forecastWeather.latitude, longitude = forecastWeather.longitude)
                uiState.update {
                    when(result){
                        is Result.Success -> {
                            it.copy(isLoading = false, tempItems = result.data.filter { item ->

                                date.toLocalDate().isEqual(DateUtils.utcToDate(item.dateTime).toLocalDate())
                            })
                        }
                        is Result.Error -> {
                            it.copy(errorMessage = result.exception.message, isLoading = true)
                        }
                    }
                }
            } catch (e: IOException){
                uiState.update {
                    ForecastWeatherUiState(
                        isLoading = false,
                        errorMessage = e.message
                    )
                }
            }
        }
    }

    fun getDate(): String {
        return "${date.dayOfMonth} ${MessageUtils.makeWord(date.month.name)}"
    }

}

data class ForecastWeatherUiState(
    val isLoading: Boolean = false,
    val tempItems: List<Temp> = listOf(),
    val errorMessage: String? = null,
)