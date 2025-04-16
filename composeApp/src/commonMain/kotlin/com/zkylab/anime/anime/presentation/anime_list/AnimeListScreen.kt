package com.zkylab.anime.anime.presentation.anime_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_anime.composeapp.generated.resources.Res
import cmp_anime.composeapp.generated.resources.favorites
import cmp_anime.composeapp.generated.resources.no_favorite_anime
import cmp_anime.composeapp.generated.resources.no_search_results
import cmp_anime.composeapp.generated.resources.search_results
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.anime.presentation.anime_list.components.AnimeList
import com.zkylab.anime.anime.presentation.anime_list.components.AnimeSearchBar
import com.zkylab.anime.anime.presentation.anime_list.components.EmptyStateMessage
import com.zkylab.anime.anime.presentation.anime_list.components.ErrorMessage
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AnimeListScreenRoot(
    viewModel: AnimeListViewModel = koinViewModel(),
    onAnimeClick: (Anime) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnimeListScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is AnimeListAction.OnAnimeClick -> onAnimeClick(action.anime)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun AnimeListScreen(
    state: AnimeListState,
    onAction: (AnimeListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val pagerState = rememberPagerState { 2 }
    val searchResultsListState = rememberLazyListState()
    val favoriteAnimeListState = rememberLazyListState()

    LaunchedEffect(state.searchResults, state.isNewSearch) {
        if (state.isNewSearch) {
            searchResultsListState.animateScrollToItem(0)
            onAction(AnimeListAction.ClearNewSearchFlag)
        }
    }

    LaunchedEffect(state.selectedTabIndex) {
        pagerState.animateScrollToPage(state.selectedTabIndex)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(AnimeListAction.OnTabSelected(pagerState.currentPage))
    }

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 2.dp,
                shadowElevation = 2.dp
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(vertical = 8.dp)
                ) {
                    Text(
                        text = "Anime Explorer",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    AnimeSearchBar(
                        searchQuery = state.searchQuery,
                        onSearchQueryChange = {
                            onAction(AnimeListAction.OnSearchQueryChange(it))
                        },
                        onImeSearch = {
                            keyboardController?.hide()
                        },
                        modifier = Modifier
                            .widthIn(max = 600.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TabRow(
                selectedTabIndex = state.selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                indicator = { tabPositions ->
                    TabRowDefaults.PrimaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTabIndex]),
                        width = 40.dp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                Tab(
                    selected = state.selectedTabIndex == 0,
                    onClick = { onAction(AnimeListAction.OnTabSelected(0)) },
                    text = {
                        Text(
                            text = stringResource(Res.string.search_results),
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Tab(
                    selected = state.selectedTabIndex == 1,
                    onClick = { onAction(AnimeListAction.OnTabSelected(1)) },
                    text = {
                        Text(
                            text = stringResource(Res.string.favorites),
                            style = MaterialTheme.typography.titleSmall
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null
                        )
                    },
                    selectedContentColor = MaterialTheme.colorScheme.primary,
                    unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize()
            ) { pageIndex ->
                when (pageIndex) {
                    0 -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (state.isLoading) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.primary,
                                    strokeWidth = 3.dp
                                )
                            } else {
                                when {
                                    state.errorMessage != null -> {
                                        ErrorMessage(
                                            message = state.errorMessage.asString(),
                                            onRetry = { onAction(AnimeListAction.LoadMore) }
                                        )
                                    }

                                    state.searchResults.isEmpty() -> {
                                        EmptyStateMessage(
                                            message = stringResource(Res.string.no_search_results),
                                            icon = Icons.Default.Search
                                        )
                                    }

                                    else -> {
                                        AnimeList(
                                            anime = state.searchResults,
                                            onAnimeClick = { onAction(AnimeListAction.OnAnimeClick(it)) },
                                            scrollState = searchResultsListState,
                                            onLoadMore = { onAction(AnimeListAction.LoadMore) },
                                            isLoadingMore = state.isLoadingMore,
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                    }

                    1 -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            if (state.favoriteAnime.isEmpty()) {
                                EmptyStateMessage(
                                    message = stringResource(Res.string.no_favorite_anime),
                                    icon = Icons.Default.Favorite
                                )
                            } else {
                                AnimeList(
                                    anime = state.favoriteAnime,
                                    onAnimeClick = { onAction(AnimeListAction.OnAnimeClick(it)) },
                                    scrollState = favoriteAnimeListState,
                                    modifier = Modifier.fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}