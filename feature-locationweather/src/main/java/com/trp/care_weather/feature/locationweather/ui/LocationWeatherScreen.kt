package com.trp.care_weather.feature.locationweather.ui

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.trp.care_weather.core.data.di.weatherDummy
import com.trp.care_weather.core.data.model.DailyWeather
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core.ui.MyApplicationTheme
import com.trp.care_weather.core.ui.PrimaryColor
import com.trp.care_weather.core.ui.PrimaryFontColor
import com.trp.care_weather.core.ui.Typography
import com.trp.care_weather.core.ui.R
import com.trp.care_weather.core.ui.SecondaryColor
import com.trp.care_weather.core.ui.composes.AirPollutionItem
import com.trp.care_weather.core.ui.composes.TempForecastItem
import kotlinx.coroutines.flow.update
import kotlin.collections.forEach

@SuppressLint("MissingPermission")
@Composable
fun LocationWeatherScreen(
    viewModel: LocationWeatherViewModel = hiltViewModel(),
    onBackStack: () -> Unit,
){
    
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.errorMessage != null){
        Toast.makeText(LocalContext.current, uiState.errorMessage, Toast.LENGTH_SHORT).show()
        viewModel.uiState.update { it.copy(errorMessage = null) }
    }

    LocationWeatherScreen(
        onBackStack = onBackStack,
        titleName = "Location Weather",
        weather = uiState.dailyWeather,
        onSearchLocation = { locationName ->
            Log.d("TAG", "LocationWeatherScreen: $locationName")
            viewModel.fetchWeatherByLocationName(locationName)
        },
    )

}

@Composable
internal fun LocationWeatherScreen(
    onBackStack: () -> Unit,
    titleName: String,
    weather: DailyWeather?,
    onSearchLocation: (String) -> Unit,
){
    var locationInput by remember { mutableStateOf("") }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            LocationWeatherTopAppBar(
                onNavigateBack = onBackStack
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            LocationSearchSection(
                locationInput = locationInput,
                onLocationInputChange = { locationInput = it},
                onSearchClick = {
                    if (locationInput.isEmpty()){
                        Toast.makeText(context, "Please enter location name", Toast.LENGTH_SHORT).show()
                        return@LocationSearchSection
                    }
                    onSearchLocation(locationInput)
                }
            )

            if (weather == null){
                return@Column
            }

            Spacer(modifier = Modifier.height(16.dp))

            WeatherDetails(
                modifier = Modifier.padding(horizontal = 16.dp),
                weather = weather
            )

            Spacer(modifier = Modifier.height(32.dp))

            TempForecastPanel(
                modifier = Modifier.fillMaxWidth(),
                temps = weather.forecastWeatherToday(),
                onItemSelected = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationWeatherTopAppBar(
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = { Text("Location Weather") },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Navigate back")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = SecondaryColor,
            titleContentColor = PrimaryFontColor,
            navigationIconContentColor = PrimaryColor
        )
    )
}

@Composable
fun LocationSearchSection(
    locationInput: String,
    onLocationInputChange: (String) -> Unit,
    onSearchClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .background(color = SecondaryColor)
            .padding(16.dp),
    ) {
        TextField(
            value = locationInput,
            onValueChange = onLocationInputChange,
            label = { Text("Enter location name") },
            modifier = Modifier.weight(1f),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = SecondaryColor,
                unfocusedTextColor = SecondaryColor,
                focusedContainerColor = PrimaryColor,
                unfocusedContainerColor = PrimaryColor,
                focusedIndicatorColor = SecondaryColor,
                unfocusedIndicatorColor = SecondaryColor,
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        OutlinedButton( // Or TextButton for less emphasis
            onClick = onSearchClick,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(width = 1.dp, color = PrimaryColor)
        ) {
            Text(text = "Search", color = PrimaryColor)
        }
    }
}

@Composable
fun WeatherDetails(
    weather: DailyWeather,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Current location temp
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top, // Align text to top, icon might be larger
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(weather.temp?.locationName?: "N/A", style = MaterialTheme.typography.headlineMedium)
                Text(weather.temp!!.tempDisplay, style = MaterialTheme.typography.displayLarge.copy(fontWeight = FontWeight.Bold))
            }
            Icon(
                painterResource(R.drawable.ic_pressure_24),
                contentDescription = "Current weather condition",
                modifier = Modifier.size(100.dp) // Adjust size as needed
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pollutant Info panel
        val airPollution = weather.airPollution!!
        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(airPollution.aqiDisplay, color = PrimaryFontColor, style = Typography.bodySmall)
            Spacer(Modifier.size(25.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                AirPollutionItem(label = "Ozone", value = airPollution.ozoneDisplay)
            }
            Spacer(Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AirPollutionItem(label = "PM 2.5", value = airPollution.pm25Display)
                AirPollutionItem(label = "PM 10", value = airPollution.pm10Display)
            }
        }
    }
}

@Composable
fun TempForecastPanel(
    modifier: Modifier = Modifier,
    temps: List<Temp>,
    onItemSelected: (Temp) -> Unit,
){
    val state = rememberScrollState()
    Row(modifier = modifier.scrollable(state = state, orientation = Orientation.Horizontal),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        temps.forEach { item ->
            TempForecastItem(item = item, onItemSelected = onItemSelected, color = SecondaryColor)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun LocationWeatherScreenPreview(){
    val weather = weatherDummy
    MyApplicationTheme {
        LocationWeatherScreen(onBackStack = {}, titleName = "28 January", weather = weather, onSearchLocation = {})
    }
}
