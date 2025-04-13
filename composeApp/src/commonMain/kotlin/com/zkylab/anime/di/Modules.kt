package com.zkylab.anime.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.zkylab.anime.anime.data.database.DatabaseFactory
import com.zkylab.anime.anime.data.database.FavoriteAnimeDatabase
import com.zkylab.anime.anime.data.network.KtorRemoteAnimeDataSource
import com.zkylab.anime.anime.data.network.RemoteAnimeDataSource
import com.zkylab.anime.anime.data.repository.DefaultAnimeRepository
import com.zkylab.anime.anime.domain.AnimeRepository
import com.zkylab.anime.anime.presentation.SelectedAnimeViewModel
import com.zkylab.anime.anime.presentation.anime_detail.AnimeDetailViewModel
import com.zkylab.anime.anime.presentation.anime_list.AnimeListViewModel
import com.zkylab.anime.core.data.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemoteAnimeDataSource).bind<RemoteAnimeDataSource>()
    singleOf(::DefaultAnimeRepository).bind<AnimeRepository>()

    single {
        get<DatabaseFactory>().create()
            .setDriver(BundledSQLiteDriver())
            .build()
    }
    single { get<FavoriteAnimeDatabase>().favoriteAnimeDao }

    viewModelOf(::AnimeListViewModel)
    viewModelOf(::AnimeDetailViewModel)
    viewModelOf(::SelectedAnimeViewModel)
}