@file:OptIn(FlowPreview::class)

package com.zkylab.anime.anime.presentation.anime_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.anime.domain.AnimeRepository
import com.zkylab.anime.core.domain.onError
import com.zkylab.anime.core.domain.onSuccess
import com.zkylab.anime.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AnimeListViewModel(
    private val repository: AnimeRepository
) : ViewModel() {
    private var cachedAnime = emptyList<Anime>()
    private var searchJob: Job? = null
    private var observeFavoriteJob: Job? = null

    private val _state = MutableStateFlow(AnimeListState())
    val state = _state
        .onStart {
            if(cachedAnime.isEmpty()) {
                observeSearchQuery()
            }
            observeFavoriteAnime()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: AnimeListAction) {
        when (action) {
            is AnimeListAction.OnAnimeClick -> {

            }

            is AnimeListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }

            is AnimeListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }

            AnimeListAction.LoadMore -> loadMoreAnime()
            AnimeListAction.ClearNewSearchFlag -> {
                _state.update { it.copy(isNewSearch = false) }
            }
        }
    }

    private fun observeFavoriteAnime() {
        observeFavoriteJob?.cancel()
        observeFavoriteJob = repository
            .getFavoriteAnime()
            .onEach { favoriteAnime ->
                _state.update { it.copy(
                    favoriteAnime = favoriteAnime
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun observeSearchQuery() {
        state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                if (query.isBlank()) {
                    _state.update {
                        it.copy(
                            errorMessage = null,
                            searchResults = cachedAnime
                        )
                    }
                } else if (query.length >= 2) {
                    searchJob?.cancel()
                    searchJob = searchAnime(query, page = 1)
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchAnime(query: String, page: Int, append: Boolean = false) = viewModelScope.launch {
        if (!append) {
            _state.update { it.copy(isLoading = true) }
        }

        repository.searchAnime(query, page)
            .onSuccess { result ->
                cachedAnime = if (append) cachedAnime + result.anime else result.anime
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        errorMessage = null,
                        searchResults = if (append) it.searchResults + result.anime else result.anime,
                        hasNextPage = result.hasNextPage,
                        currentPage = page,
                        canLoadMore = result.hasNextPage,
                        isNewSearch = !append
                    )
                }
            }
            .onError { error ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        errorMessage = error.toUiText()
                    )
                }
            }
    }

    private fun loadMoreAnime() {
        val currentState = state.value
        if (currentState.isLoadingMore || !currentState.canLoadMore || currentState.searchQuery.isBlank()) return

        _state.update { it.copy(isLoadingMore = true) }

        val nextPage = currentState.currentPage + 1
        searchAnime(currentState.searchQuery, page = nextPage, append = true)
    }


}