package com.example.weathercompose.data


import com.google.gson.annotations.SerializedName

data class WeatherModel(
    @SerializedName("current")
    val current: Current,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)