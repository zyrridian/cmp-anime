package com.zkylab.anime.anime.presentation.home

import com.zkylab.anime.core.presentation.UiText

data class HomeState(
    val isLoading: Boolean = true,
    val error: UiText? = null,
    val selectedBottomTab: BottomNavItem = BottomNavItem.HOME,
    val featuredItems: List<FeaturedItem> = emptyList(),
    val animeCategories: List<AnimeCategory> = emptyList(),
    val mangaCategories: List<MangaCategory> = emptyList(),
    val exploreCategories: List<ExploreCategory> = emptyList()
)