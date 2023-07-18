package com.example.weathercompose.data


import com.google.gson.annotations.SerializedName

data class Day(

    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("maxtemp_c")
    val maxtempC: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
)