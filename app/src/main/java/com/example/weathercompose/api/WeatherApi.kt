package com.example.weathercompose.api

import com.example.weathercompose.data.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "5d5e923b12dd4c59b31155941230407"

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getData(
        @Query("key") apiKey: String = API_KEY,
        @Query("q")      city: String,
        @Query("days")   days: Int,
        @Query("aqi")    aqi: String,
        @Query("alerts") alerts: String
    ): Response<WeatherModel>
}