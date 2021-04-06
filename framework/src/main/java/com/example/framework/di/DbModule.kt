package com.example.framework.di

import androidx.room.Room
import com.example.framework.sources.db.AppDatabase
import com.example.framework.sources.db.DB_NAME
import org.koin.dsl.module

val dbModule = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()

    }

    factory {
        val db: AppDatabase = get()
        db.charactersDao()
    }

    factory {
        val db: AppDatabase = get()
        db.searchDao()
    }

    factory {
        val db: AppDatabase = get()
        db.episodesDao()
    }
}