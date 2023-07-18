package com.example.weathercompose.api

import android.util.Log
import com.example.weathercompose.data.WeatherModel
import com.google.gson.Gson

class Repository(val weatherApi: WeatherApi) {
     suspend fun getData(city:String, days: Int): WeatherModel? {
        val response = weatherApi.getData(city=city,days=days,aqi="no",alerts="no")
        return if (response.isSuccessful && response.body() != null) {
            response.body()!!
        } else {
          null
        }
    }
}