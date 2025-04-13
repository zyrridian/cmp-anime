package com.zkylab.anime.anime.presentation.anime_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextAlign
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
import com.zkylab.anime.core.presentation.DarkBlue
import com.zkylab.anime.core.presentation.DesertWhite
import com.zkylab.anime.core.presentation.SandYellow
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize().background(DarkBlue).statusBarsPadding()
    ) {
        AnimeSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(AnimeListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {
                keyboardController?.hide()
            },
            modifier = Modifier
                .widthIn(max = 400.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )
        Surface(
            color = DesertWhite,
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            modifier = Modifier.weight(1f).fillMaxWidth()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TabRow(
                    selectedTabIndex = state.selectedTabIndex,
                    containerColor = DesertWhite,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            color = SandYellow,
                            modifier = Modifier.tabIndicatorOffset(tabPositions[state.selectedTabIndex])
                        )
                    },
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .widthIn(max = 700.dp)
                        .fillMaxWidth()
                ) {
                    Tab(
                        selected = state.selectedTabIndex == 0,
                        onClick = {
                            onAction(AnimeListAction.OnTabSelected(0))
                        },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(Res.string.search_results),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                    Tab(
                        selected = state.selectedTabIndex == 1,
                        onClick = {
                            onAction(AnimeListAction.OnTabSelected(1))
                        },
                        selectedContentColor = SandYellow,
                        unselectedContentColor = Color.Black.copy(alpha = 0.5f),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = stringResource(Res.string.favorites),
                            modifier = Modifier.padding(vertical = 12.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth().weight(1f)
                ) { pageIndex ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        when (pageIndex) {
                            0 -> {
                                if (state.isLoading) {
                                    CircularProgressIndicator()
                                } else {
                                    when {
                                        state.errorMessage != null -> {
                                            Text(
                                                text = state.errorMessage.asString(),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        state.searchResults.isEmpty() -> {
                                            Text(
                                                text = stringResource(Res.string.no_search_results),
                                                textAlign = TextAlign.Center,
                                                style = MaterialTheme.typography.headlineSmall,
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }

                                        else -> {
                                            AnimeList(
                                                anime = state.searchResults,
                                                onAnimeClick = {
                                                    onAction(
                                                        AnimeListAction.OnAnimeClick(
                                                            it
                                                        )
                                                    )
                                                },
                                                scrollState = searchResultsListState,
                                                onLoadMore = { onAction(AnimeListAction.LoadMore) },
                                                isLoadingMore = state.isLoadingMore,
                                                modifier = Modifier.fillMaxSize(),
                                            )
                                        }
                                    }
                                }
                            }

                            1 -> {
                                if (state.favoriteAnime.isEmpty()) {
                                    Text(
                                        text = stringResource(Res.string.no_favorite_anime),
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.headlineSmall,
                                    )
                                } else {
                                    AnimeList(
                                        anime = state.favoriteAnime,
                                        onAnimeClick = {
                                            onAction(AnimeListAction.OnAnimeClick(it))
                                        },
                                        modifier = Modifier.fillMaxSize(),
                                        scrollState = favoriteAnimeListState
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}