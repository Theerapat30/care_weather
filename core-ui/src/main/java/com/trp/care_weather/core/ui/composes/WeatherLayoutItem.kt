package com.trp.care_weather.core.ui.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.trp.care_weather.core.data.di.tempDummy
import com.trp.care_weather.core.data.model.Temp
import com.trp.care_weather.core.ui.MyApplicationTheme
import com.trp.care_weather.core.ui.PrimaryColor
import com.trp.care_weather.core.ui.PrimaryFontColor
import com.trp.care_weather.core.ui.R
import com.trp.care_weather.core.ui.SecondaryFontColor
import com.trp.care_weather.core.ui.Typography

@Composable
fun TempForecastItem(item: Temp, onItemSelected: (Temp) -> Unit, color: Color = PrimaryFontColor){
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
            Text(text = item.tempDisplay, color = color, style = Typography.bodySmall)
            Icon(painterResource(R.drawable.ic_pressure_24), contentDescription = null, tint = PrimaryColor)
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = item.dayMonthDisplay(), color = color, style = Typography.bodySmall)
                Text(text = item.time(), color = color, style = Typography.bodySmall)
            }
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
fun WeatherInfoItem(
    modifier: Modifier = Modifier,
    iconId: Int,
    label: String,
    info: String
){
    val spaceSize = 8.dp
    Row (
        modifier = modifier
            .width(250.dp)
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(iconId), contentDescription = null)
            Spacer(modifier = Modifier.size(spaceSize))
            Text(
                label,
                modifier = Modifier
                    .padding(4.dp),
                style = Typography.titleLarge
            )
        }
        
        Text(info, style = Typography.bodyLarge)
    }
}

@Composable
fun ForecastItem(
    modifier: Modifier = Modifier,
    time: String,
    iconId: Int,
    tempInfo: String,
){
    Box(
        modifier = modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = 1.dp, Color.Black),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 4.dp, vertical = 8.dp),
    ) {
        Text(text = time, modifier = Modifier.align(Alignment.CenterStart), color = SecondaryFontColor)
        Row(
            modifier = Modifier
                .align(Alignment.Center)
                .width(100.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(painter = painterResource(iconId), contentDescription = null, tint = SecondaryFontColor)
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = tempInfo, color = SecondaryFontColor)
        }
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
fun AirPollutionItemPreview(){
    MyApplicationTheme {
        AirPollutionItem(label = "Ozone", value = "54.36")
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherInfoItemPreview(){
    MyApplicationTheme {
        WeatherInfoItem(
            modifier = Modifier,
            iconId = R.drawable.ic_wind_24,
            label = "Weather",
            info = "30"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ForecastItemPreview(){
    MyApplicationTheme {
        ForecastItem(
            modifier = Modifier,
            time = "08:30",
            iconId = R.drawable.ic_wind_24,
            tempInfo = "24"
        )
    }
}