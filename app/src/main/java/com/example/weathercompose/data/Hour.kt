package com.example.weathercompose.data


import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("time")
    val time: String,

)