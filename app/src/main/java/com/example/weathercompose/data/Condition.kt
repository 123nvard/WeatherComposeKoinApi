package com.example.weathercompose.data


import com.google.gson.annotations.SerializedName

data class Condition(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)