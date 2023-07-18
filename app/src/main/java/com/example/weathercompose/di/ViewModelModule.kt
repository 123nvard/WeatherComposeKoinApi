package com.example.weathercompose.di

import com.example.weathercompose.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel { WeatherViewModel(get()) }
}