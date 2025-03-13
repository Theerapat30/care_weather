package com.trp.care_weather.core.ui.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.trp.care_weather.core.ui.MyApplicationTheme
import com.trp.care_weather.core.ui.R
import com.trp.care_weather.core.ui.SecondaryFontColor
import com.trp.care_weather.core.ui.Typography

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