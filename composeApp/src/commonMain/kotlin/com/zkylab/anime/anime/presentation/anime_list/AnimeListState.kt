package com.zkylab.anime.anime.presentation.anime_list

import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.core.presentation.UiText

data class AnimeListState(
    val searchQuery: String = "",
    val searchResults: List<Anime> = emptyList(),
    val favoriteAnime: List<Anime> = emptyList(),
    val isLoading: Boolean = false,
    val selectedTabIndex: Int = 0,
    val hasNextPage: Boolean = false,
    val errorMessage: UiText? = null,
    val isLoadingMore: Boolean = false,
    val canLoadMore: Boolean = true,
    val currentPage: Int = 1,
    val isNewSearch: Boolean = false
)
