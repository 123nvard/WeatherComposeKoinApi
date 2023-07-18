package com.example.weathercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.lifecycleScope
import com.example.weathercompose.data.WeatherModel
import com.example.weathercompose.screens.DialogSearch
import com.example.weathercompose.screens.MainCard
import com.example.weathercompose.screens.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel: WeatherViewModel by viewModel()
            var objectOfWeather by remember {
                mutableStateOf<WeatherModel?>(null)
            }
            val dialogState= remember {
                mutableStateOf<Boolean  >(false)
            }

            val query by remember { mutableStateOf("London") }

            LaunchedEffect(true) {
                val weatherData = fetchData(query, 3, viewModel)
                objectOfWeather = weatherData
            }
            if (dialogState.value){
                DialogSearch(dialogState, onSubmit = {
                    lifecycleScope.launch {
                        val weatherData = fetchData(it, 3, viewModel)
                        objectOfWeather = weatherData
                    }
                })
            }
            Image(
                painter = painterResource(id = R.drawable.background_image),
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.5f,
                contentDescription = "back_image"
            )
            Column {

                objectOfWeather?.let {
                    MainCard(it, {
                      lifecycleScope.launch {
                          val weatherData = fetchData(query, 3, viewModel)
                          objectOfWeather = weatherData
                      }
                    },{
                        dialogState.value=true
                    })
                    TabLayout(it)
//                    if (query.isNullOrEmpty()){
//                        LaunchedEffect(true) {
//                            val weatherData = fetchData("London", 3, viewModel)
//                            objectOfWeather = weatherData
//
//                        }
//                    }else{
//                        LaunchedEffect(true) {
//                            val weatherData = fetchData(query!!, 3, viewModel)
//                            objectOfWeather = weatherData
//                        }
//                    }
                }

            }

        }
    }
}

private suspend fun getData(city: String, days: Int, viewModel: WeatherViewModel): WeatherModel {
    viewModel.getData(city, days)
    delay(3000)
    return viewModel.weatherData!!
}

suspend fun fetchData(city: String, days: Int, viewModel: WeatherViewModel): WeatherModel {
    return withContext(Dispatchers.IO) {
        getData(city, days, viewModel)
    }
}