package com.zkylab.anime.anime.presentation.home

import com.zkylab.anime.anime.data.dto.AnimeTopDto
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.manga.domain.Manga

sealed class HomeAction {
    object LoadContent : HomeAction()
    data class OnBottomTabSelected(val tab: BottomNavItem) : HomeAction()
    data class OnAnimeClick(val anime: AnimeTopDto) : HomeAction()
    data class OnMangaClick(val manga: AnimeTopDto) : HomeAction()
    data class OnViewAllAnimeClick(val category: AnimeCategory) : HomeAction()
    data class OnViewAllMangaClick(val category: MangaCategory) : HomeAction()
    data class OnExploreCategoryClick(val category: ExploreCategory) : HomeAction()
    object OnSearchClick : HomeAction()
    object OnFavoritesClick : HomeAction()
}