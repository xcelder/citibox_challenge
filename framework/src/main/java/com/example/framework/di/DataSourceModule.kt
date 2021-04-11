package com.example.framework.di

import com.example.data.characters.datasources.InMemoryCharactersDataSource
import com.example.data.characters.datasources.NetworkCharactersDataSource
import com.example.data.episodes.datasources.InMemoryEpisodesDataSource
import com.example.data.episodes.datasources.NetworkEpisodesDataSource
import com.example.framework.features.characters.datasources.InMemoryCharacterDataSourceImpl
import com.example.framework.features.characters.datasources.NetworkCharactersDataSourceImpl
import com.example.framework.features.episodes.datasources.InMemoryEpisodesDataSourceImpl
import com.example.framework.features.episodes.datasources.NetworkEpisodesDataSourceImpl
import org.koin.dsl.module

val dataSourceModule = module {

    factory<NetworkCharactersDataSource> {
        NetworkCharactersDataSourceImpl(get())
    }

    factory<InMemoryCharactersDataSource> {
        InMemoryCharacterDataSourceImpl(get())
    }

    factory<NetworkEpisodesDataSource> {
        NetworkEpisodesDataSourceImpl(get())
    }

    factory<InMemoryEpisodesDataSource> {
        InMemoryEpisodesDataSourceImpl(get())
    }
}