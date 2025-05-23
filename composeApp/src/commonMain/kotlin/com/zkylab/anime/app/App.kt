package com.zkylab.anime.app

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.zkylab.anime.anime.presentation.SelectedAnimeViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import com.zkylab.anime.anime.presentation.anime_detail.AnimeDetailAction
import com.zkylab.anime.anime.presentation.anime_detail.AnimeDetailScreenRoot
import com.zkylab.anime.anime.presentation.anime_detail.AnimeDetailViewModel
import com.zkylab.anime.anime.presentation.anime_list.AnimeListScreenRoot
import com.zkylab.anime.anime.presentation.anime_list.AnimeListViewModel
import com.zkylab.anime.anime.presentation.home.HomeScreenRoot
import com.zkylab.anime.anime.presentation.home.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.AnimeGraph
        ) {
            navigation<Route.AnimeGraph>(
                startDestination = Route.AnimeList
            ) {
                composable<Route.AnimeList>(
                    exitTransition = { slideOutHorizontally() },
                    popEnterTransition = { slideInHorizontally() }
                ) {
                    val viewModel = koinViewModel<AnimeListViewModel>()
                    val viewModelHome = koinViewModel<HomeViewModel>()
                    val selectedAnimeViewModel =
                        it.sharedKoinViewModel<SelectedAnimeViewModel>(navController)

                    LaunchedEffect(true) {
                        selectedAnimeViewModel.onSelectAnime(null)
                    }
                    HomeScreenRoot(
                        viewModel = viewModelHome,
                        onAnimeClick = { anime ->
//                            selectedAnimeViewModel.onSelectAnime(anime)
//                            navController.navigate(
//                                Route.AnimeDetail(anime.malId.toString())
//                            )
                        },
                        onViewAllAnimeClick = { },
                        onViewAllMangaClick = { },
                        onSearchClick = { },
                        onFavoritesClick = { },
                    )

//                    AnimeListScreenRoot(
//                        viewModel = viewModel,
//                        onAnimeClick = { anime ->
//                            selectedMangaViewModel.onSelectAnime(anime)
//                            navController.navigate(
//                                Route.AnimeDetail(anime.malId.toString())
//                            )
//                        }
//                    )
                }

                composable<Route.AnimeDetail>(
                    enterTransition = {
                        slideInHorizontally { initialOffset ->
                            initialOffset
                        }
                    },
                    exitTransition = {
                        slideOutHorizontally { initialOffset ->
                            initialOffset
                        }
                    }
                ) {
                    val selectedBookViewModel =
                        it.sharedKoinViewModel<SelectedAnimeViewModel>(navController)
                    val viewModel = koinViewModel<AnimeDetailViewModel>()
                    val selectedBook by selectedBookViewModel.selectedAnime.collectAsStateWithLifecycle()

                    LaunchedEffect(selectedBook) {
                        selectedBook?.let {
                            viewModel.onAction(AnimeDetailAction.OnSelectedAnimeChange(it))
                        }
                    }

                    AnimeDetailScreenRoot(
                        viewModel = viewModel,
                        onBackClick = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}


@Composable
private inline fun <reified T : ViewModel> NavBackStackEntry.sharedKoinViewModel(
    navController: NavController
): T {
    val navGraphRoute = destination.parent?.route ?: return koinViewModel<T>()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return koinViewModel(
        viewModelStoreOwner = parentEntry
    )
}