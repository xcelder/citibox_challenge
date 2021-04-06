package com.example.framework.di

import com.example.framework.sources.network.retrofit.RetroClient
import org.koin.dsl.module

val networkModule = module {

    factory { RetroClient.charactersApi }

    factory { RetroClient.episodesApi }
}
