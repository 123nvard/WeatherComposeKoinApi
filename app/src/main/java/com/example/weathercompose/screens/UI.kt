package com.example.weathercompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.data.Forecastday
import com.example.weathercompose.data.Hour
import com.example.weathercompose.ui.theme.Blue10

@Composable
fun ListItemByDays(forecast: Forecastday) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        backgroundColor = Blue10,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp)) {
                Text(text = forecast.date, color = Color.Blue)
                Text(text = forecast.day.condition.text, color = Color.White)
            }
            Text(
                text = "${forecast.day.maxtempC}ºC/${forecast.day.mintempC}ºC",
                color = Color.White,
                style = TextStyle(fontSize = 25.sp)
            )
            AsyncImage(
                model = "https:${forecast.day.condition.icon}",
                contentDescription = "icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(45.dp)
            )
        }
    }
}

@Composable
fun ListItemByHour(hour: Hour) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 3.dp),
        backgroundColor = Blue10,
        elevation = 0.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(start = 8.dp, top = 5.dp, bottom = 5.dp)) {
                Text(text = hour.time, color = Color.Blue)
                Text(text = hour.condition.text, color = Color.White)
            }
            Text(text = "${hour.tempC}ºC", color = Color.White, style = TextStyle(fontSize = 25.sp))
            AsyncImage(
                model = "https:${hour.condition.icon}",
                contentDescription = "icon",
                modifier = Modifier
                    .padding(end = 8.dp)
                    .size(45.dp)
            )
        }
    }
}

@Composable
fun DialogSearch(dialogState:MutableState<Boolean>, onSubmit:(String)->Unit) {
    val dialogText=remember{
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {
                                   dialogState.value=false
    }, confirmButton = {
        TextButton(onClick = {
            onSubmit.invoke(dialogText.value)
            dialogState.value=false
        }) {
            Text(text = "OK")

        }
    },
        dismissButton = {
            TextButton(onClick = {
                dialogState.value=false

            }) {
                Text(text = "Cancel")
            }
        },
        title = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Write name of city")
                TextField(value = dialogText.value, onValueChange ={
dialogText.value=it
                } )

            }
        }
    )
}