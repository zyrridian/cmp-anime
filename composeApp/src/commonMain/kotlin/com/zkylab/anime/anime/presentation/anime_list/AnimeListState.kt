package com.zkylab.anime.anime.presentation.anime_list

import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.core.presentation.UiText


data class AnimeListState(
    val searchQuery: String = "Naruto",
    val searchResults: List<Anime> = emptyList(),
    val favoriteAnime: List<Anime> = emptyList(),
    val isLoading: Boolean = true,
    val selectedTabIndex: Int = 0,
    val errorMessage: UiText? = null
)