package com.zkylab.anime.anime.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zkylab.anime.anime.data.dto.AnimeTopDto
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.anime.domain.AnimeRepository
import com.zkylab.anime.core.domain.DataError
import com.zkylab.anime.core.domain.onError
import com.zkylab.anime.core.domain.onSuccess
import com.zkylab.anime.core.presentation.UiText
import com.zkylab.anime.core.presentation.toUiText
import com.zkylab.anime.manga.domain.Manga
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.coroutineScope

class HomeViewModel(
    private val animeRepository: AnimeRepository,
    private val mangaRepository: AnimeRepository
) : ViewModel() {

    private var loadJob: Job? = null

    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            loadContent()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.LoadContent -> loadContent()
            is HomeAction.OnBottomTabSelected -> updateSelectedTab(action.tab)
            is HomeAction.OnAnimeClick -> {
                // Handle OnAnimeClick action
            }
            is HomeAction.OnMangaClick -> {
                // Handle OnMangaClick action
            }
            is HomeAction.OnViewAllAnimeClick -> {
                // Handle OnViewAllAnimeClick action
            }
            is HomeAction.OnViewAllMangaClick -> {
                // Handle OnViewAllMangaClick action
            }
            is HomeAction.OnExploreCategoryClick -> {
                // Handle OnExploreCategoryClick action
            }
            is HomeAction.OnSearchClick -> {
                // Handle OnSearchClick action
            }
            is HomeAction.OnFavoritesClick -> {
                // Handle OnFavoritesClick action
            }
            else -> {
                // Optional: Add a default case to handle unknown actions
            }
        }
    }


    private fun updateSelectedTab(tab: BottomNavItem) {
        _state.update { it.copy(selectedBottomTab = tab) }
    }

    private fun loadContent() {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                coroutineScope {
                    val topAnimeDeferred = animeRepository.getTopAnime(
                        page = 1,
                        limit = 10,
                        type = "tv",
                        filter = "bypopularity",
                        rating = null,
                        sfw = true
                    )

                    val airingAnimeDeferred = animeRepository.getTopAnime(
                        page = 1,
                        limit = 10,
                        type = "tv",
                        filter = "airing",
                        rating = null,
                        sfw = true
                    )

                    val upcomingAnimeDeferred = animeRepository.getTopAnime(
                        page = 1,
                        limit = 10,
                        type = "tv",
                        filter = "upcoming",
                        rating = null,
                        sfw = true
                    )

                    val popularMangaDeferred = mangaRepository.getTopAnime(
                        page = 1,
                        limit = 10,
                        type = "manga",
                        filter = "bypopularity",
                        rating = null,
                        sfw = true
                    )

                    val newestMangaDeferred = mangaRepository.getTopAnime(
                        page = 1,
                        limit = 10,
                        type = "manga",
                        filter = "publishing",
                        rating = null,
                        sfw = true
                    )

                    val topMangaDeferred = mangaRepository.getTopAnime(
                        page = 1,
                        limit = 10,
                        type = "manga",
                        filter = "favorite",
                        rating = null,
                        sfw = true
                    )

                    var topAnime = emptyList<AnimeTopDto>()
                    var airingAnime = emptyList<AnimeTopDto>()
                    var upcomingAnime = emptyList<AnimeTopDto>()
                    var popularManga = emptyList<AnimeTopDto>()
                    var newestManga = emptyList<AnimeTopDto>()
                    var topManga = emptyList<AnimeTopDto>()

                    topAnimeDeferred.onSuccess { result ->
                        topAnime = result.data?.map { it } ?: emptyList()
                    }.onError { setError(it) }

                    airingAnimeDeferred.onSuccess { result ->
                        airingAnime = result.data?.map { it } ?: emptyList()
                    }.onError { setError(it) }

                    upcomingAnimeDeferred.onSuccess { result ->
                        upcomingAnime = result.data?.map { it } ?: emptyList()
                    }.onError { setError(it) }

                    popularMangaDeferred.onSuccess { result ->
                        popularManga = result.data?.map { it } ?: emptyList()
                    }.onError { setError(it) }

                    newestMangaDeferred.onSuccess { result ->
                        newestManga = result.data?.map { it } ?: emptyList()
                    }.onError { setError(it) }

                    topMangaDeferred.onSuccess { result ->
                        topManga = result.data?.map { it } ?: emptyList()
                    }.onError { setError(it) }

                    val animeCategories = listOf(
                        AnimeCategory("top-anime", "Top Anime", topAnime),
                        AnimeCategory("airing-anime", "Currently Airing", airingAnime),
                        AnimeCategory("upcoming-anime", "Upcoming Anime", upcomingAnime)
                    )

                    val mangaCategories = listOf(
                        MangaCategory("popular-manga", "Popular Manga", popularManga),
                        MangaCategory("newest-manga", "Newest Releases", newestManga),
                        MangaCategory("top-manga", "Top Rated Manga", topManga)
                    )

                    val featuredItems = createFeaturedItems(topAnime, topManga)
                    val exploreCategories = createExploreCategories()

                    _state.update {
                        it.copy(
                            isLoading = false,
                            animeCategories = animeCategories,
                            mangaCategories = mangaCategories,
                            featuredItems = featuredItems,
                            exploreCategories = exploreCategories,
                            error = null
                        )
                    }
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = UiText.DynamicString(e.message ?: "Unknown error occurred")
                    )
                }
            }
        }
    }

    private fun setError(error: DataError.Remote) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error.toUiText()
            )
        }
    }

    private fun createFeaturedItems(
        topAnime: List<AnimeTopDto>,
        topManga: List<AnimeTopDto>
    ): List<FeaturedItem> {
        return (topAnime.take(3).map { FeaturedItem.AnimeFeatured(it) } +
                topManga.take(3).map { FeaturedItem.MangaFeatured(it) })
            .shuffled()
    }

    private fun createExploreCategories(): List<ExploreCategory> {
        return listOf(
            ExploreCategory("genres", "Genres", ExploreCategoryType.GENRE),
            ExploreCategory("seasons", "Seasons", ExploreCategoryType.SEASON),
            ExploreCategory("studios", "Studios", ExploreCategoryType.STUDIO),
            ExploreCategory("publishers", "Publishers", ExploreCategoryType.PUBLISHER)
        )
    }
}
