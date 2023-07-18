package com.example.weathercompose.data


import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("temp_c")
    val tempC: Double,

)