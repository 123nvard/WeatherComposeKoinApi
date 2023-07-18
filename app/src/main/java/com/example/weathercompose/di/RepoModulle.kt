package com.example.weathercompose.di

import com.example.weathercompose.api.Repository
import org.koin.dsl.module

val repoModule= module {
    factory { Repository(get()) }
}