package com.example.framework.di

import com.example.data.characters.repository.CharactersRepository
import com.example.data.episodes.repository.EpisodesRepository
import com.example.framework.features.characters.repository.CharactersRepositoryImpl
import com.example.framework.features.episodes.repository.EpisodesRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    
    factory<CharactersRepository> {
        CharactersRepositoryImpl(get(), get())
    }

    factory<EpisodesRepository> {
        EpisodesRepositoryImpl(get(), get())
    }
}