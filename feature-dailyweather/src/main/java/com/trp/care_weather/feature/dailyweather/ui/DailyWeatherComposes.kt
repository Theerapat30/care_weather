package com.trp.care_weather.feature.dailyweather.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trp.care_weather.core.data.di.airPollutionDummy
import com.trp.care_weather.core.data.di.tempDummy
import com.trp.care_weather.core.data.di.tempForecastDummy
import com.trp.care_weather.core.data.model.AirPollution
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core.ui.MyApplicationTheme
import com.trp.care_weather.core.ui.PrimaryColor
import com.trp.care_weather.core.ui.PrimaryFontColor
import com.trp.care_weather.core.ui.Typography
import com.trp.care_weather.core.ui.R
import com.trp.care_weather.core.ui.SecondaryFontColor

@Composable
fun TempPanel(
    modifier: Modifier = Modifier,
    dateString: String,
    temp: Temp
){
    Column(
        modifier = modifier.padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .width(180.dp)
                .height(100.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent.copy(alpha = 0.025f))
        ) {
            Box(modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp)) {
                Text(
                    text = dateString,
                    modifier = Modifier.align(Alignment.TopCenter),
                    style = Typography.titleSmall,
                    color = PrimaryFontColor
                )
                Text(
                    text = temp.tempDisplay,
                    modifier = Modifier.align(Alignment.Center),
                    style = Typography.headlineLarge,
                    color = PrimaryFontColor
                )
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TempSuffixItem(imgId = R.drawable.ic_thermometer_minus_24, info = temp.minTempDisplay, suffix = "min")
            TempSuffixItem(imgId = null, info = temp.tempFeelsDisplay, suffix = "feels")
            TempSuffixItem(imgId = R.drawable.ic_thermometer_add_24, info = temp.minTempDisplay, suffix = "max")
        }
    }
}

@Composable
fun TempSuffixItem(@DrawableRes imgId: Int?, info: String, suffix: String){
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imgId != null){
            Icon(
                painter = painterResource(imgId),
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.size(4.dp))
        }
        Text(text = info, color = PrimaryFontColor, style = Typography.bodyMedium)
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = suffix, color = PrimaryFontColor, style = Typography.bodyMedium)
    }
}

@Composable
fun TempForecastItem(item: Temp, onItemSelected: (Temp) -> Unit){
    Card(
        modifier = Modifier
            .width(65.dp)
            .height(110.dp),
        border = BorderStroke(width = 1.dp, color = Color.Black.copy(alpha = 0.2f)),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent.copy(alpha = 0.025f)),
        onClick = {
            onItemSelected(item)
        }
    ){
        Column(
            modifier = Modifier.padding(vertical = 8.dp).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = item.tempDisplay, color = PrimaryFontColor, style = Typography.bodySmall)
            Icon(painterResource(R.drawable.ic_pressure_24), contentDescription = null, tint = PrimaryColor)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = item.dayMonthDisplay(), color = PrimaryFontColor, style = Typography.bodySmall)
                Text(text = item.time(), color = PrimaryFontColor, style = Typography.bodySmall)
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
            TempForecastItem(item = item, onItemSelected = onItemSelected)
        }
    }
}

@Composable
fun AirPollutionItem(label: String, value: String){
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(45.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent.copy(alpha = 0.04f))
    ) {
        Box(modifier = Modifier.fillMaxSize().padding(4.dp)) {
            Text(text = label, modifier = Modifier.align(Alignment.TopStart), color = SecondaryFontColor, style = Typography.bodySmall)
            Text(text = value, modifier = Modifier.align(Alignment.BottomCenter), color = SecondaryFontColor, style = Typography.bodyMedium)
        }
    }
}

@Composable
fun AirPollutionPanel(modifier: Modifier = Modifier, airPollution: AirPollution){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(airPollution.aqiDisplay, color = PrimaryFontColor, style = Typography.bodySmall)
        Spacer(Modifier.size(25.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween

        ) {
            AirPollutionItem(label = "Ozone", value = airPollution.ozoneDisplay)
            AirPollutionItem(label = "Carbon", value = airPollution.carbonDisplay)
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

@Preview(showBackground = true, backgroundColor = 0xFFBDE3F0)
@Composable
fun TempPanelPreview(){
    val temp = tempDummy
    MyApplicationTheme {
        TempPanel(
            dateString = "Monday, 09 December",
            temp = temp
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFBDE3F0)
@Composable
fun TempForecastItemPreview(){
    val forecastTemp = tempDummy
    MyApplicationTheme {
        TempForecastItem(item = forecastTemp, onItemSelected = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFBDE3F0)
@Composable
fun AirPollutionPanelPreview(){
    val airPollution = airPollutionDummy
    MyApplicationTheme {
        AirPollutionPanel(airPollution = airPollution)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFBDE3F0)
@Composable
fun AirPollutionItemPreview(){
    MyApplicationTheme {
        AirPollutionItem(label = "Ozone", value = "54.36")
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFBDE3F0)
@Composable
fun TempForecastPanelPreview(){
    val temps = tempForecastDummy
    MyApplicationTheme {
        TempForecastPanel(modifier = Modifier.fillMaxWidth(), temps = temps, onItemSelected = {})
    }
}