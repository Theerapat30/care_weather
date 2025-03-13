package com.trp.care_weather.feature.forecastweather.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trp.care_weather.core.data.di.tempForecastDummy
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core.ui.MyApplicationTheme
import com.trp.care_weather.core.ui.PrimaryColor
import com.trp.care_weather.core.ui.PrimaryFontColor
import com.trp.care_weather.core.ui.SunnyGradientColor
import com.trp.care_weather.core.ui.Typography
import com.trp.care_weather.core.ui.R
import com.trp.care_weather.core.ui.composes.ForecastItem

@SuppressLint("MissingPermission")
@Composable
fun ForecastWeatherScreen(
    viewModel: ForecastWeatherViewModel = hiltViewModel(),
    onBackStack: () -> Unit,
){

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.tempItems.isNotEmpty()){
        ForecastWeatherScreen(
            onBackStack = onBackStack,
            titleName = viewModel.getDate(),
            temps = uiState.tempItems
        )
    }

}

@Composable
internal fun ForecastWeatherScreen(
    onBackStack: () -> Unit,
    titleName: String,
    temps: List<Temp>
){
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackStack) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null, tint = PrimaryColor)
                }
                Text(text = titleName, color = PrimaryFontColor, fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(SunnyGradientColor))
                .padding(vertical = innerPadding.calculateTopPadding(), horizontal = 16.dp)
        ) {
            items(count = temps.size) { index ->
                val temp = temps[index]
                ForecastItem(
                    modifier = Modifier.padding(bottom = 8.dp),
                    time = temp.time(),
                    iconId = R.drawable.ic_pressure_24,
                    tempInfo = temp.tempDisplay
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastWeatherLoading(onBackStack: () -> Unit){
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackStack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                    }
                },
                title = {}
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Loading", style = Typography.headlineMedium)
            LinearProgressIndicator()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun ForecastWeatherScreenPreview(){
    val items = tempForecastDummy
    MyApplicationTheme {
        ForecastWeatherScreen(onBackStack = {}, titleName = "28 January", temps = items)
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastWeatherLoadingPreview(){
    MyApplicationTheme {
        ForecastWeatherLoading(onBackStack = {})
    }
}

