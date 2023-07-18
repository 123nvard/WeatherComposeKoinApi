package com.example.weathercompose.di

import com.example.weathercompose.api.WeatherApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val serviceModule = module {
    single { getRetrofit().create(WeatherApi::class.java) }
}

private fun getRetrofit(): Retrofit {

    return Retrofit.Builder().client(OkHttpClient().newBuilder().build()).addConverterFactory(GsonConverterFactory.create())
        .baseUrl("https://api.weatherapi.com/v1/").build()
}