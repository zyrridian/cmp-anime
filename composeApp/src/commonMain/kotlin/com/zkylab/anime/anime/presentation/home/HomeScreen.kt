package com.zkylab.anime.anime.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zkylab.anime.anime.data.dto.AnimeTopDto
import com.zkylab.anime.anime.domain.Anime
import com.zkylab.anime.manga.domain.Manga
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    onAnimeClick: (AnimeTopDto) -> Unit,
//    onMangaClick: (Manga) -> Unit,
    onViewAllAnimeClick: (AnimeCategory) -> Unit,
    onViewAllMangaClick: (MangaCategory) -> Unit,
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = state,
        onAction = { action ->
            when (action) {
                is HomeAction.OnAnimeClick -> onAnimeClick(action.anime)
//                is HomeAction.OnMangaClick -> onMangaClick(action.manga)
                is HomeAction.OnViewAllAnimeClick -> onViewAllAnimeClick(action.category)
                is HomeAction.OnViewAllMangaClick -> onViewAllMangaClick(action.category)
                is HomeAction.OnSearchClick -> onSearchClick()
                is HomeAction.OnFavoritesClick -> onFavoritesClick()
                else -> viewModel.onAction(action)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    val scrollState = rememberLazyListState()

    Scaffold(
        topBar = {
            HomeTopAppBar(
                onSearchClick = { onAction(HomeAction.OnSearchClick) },
                onFavoritesClick = { onAction(HomeAction.OnFavoritesClick) }
            )
        },
        bottomBar = {
            NavigationBar {
                BottomNavItem.values().forEach { item ->
                    NavigationBarItem(
                        selected = state.selectedBottomTab == item,
                        onClick = { onAction(HomeAction.OnBottomTabSelected(item)) },
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                ErrorView(
                    errorMessage = state.error.asString(),
                    onRetry = { onAction(HomeAction.LoadContent) },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            else -> {
                LazyColumn(
                    state = scrollState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Display content based on selected bottom tab
                    when (state.selectedBottomTab) {
                        BottomNavItem.HOME -> {
                            // Featured section
                            item {
                                FeaturedCarousel(
                                    featuredItems = state.featuredItems,
                                    onAnimeClick = { onAction(HomeAction.OnAnimeClick(it)) },
                                    onMangaClick = { onAction(HomeAction.OnMangaClick(it)) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(200.dp)
                                        .padding(vertical = 8.dp)
                                )
                            }

                            // Anime Categories
                            state.animeCategories.forEach { category ->
                                item {
                                    ContentSection(
                                        title = category.title,
                                        onViewAllClick = { onAction(HomeAction.OnViewAllAnimeClick(category)) },
                                        items = category.items,
                                        onItemClick = { onAction(HomeAction.OnAnimeClick(it)) }
                                    )
                                }
                            }

                            // Manga Categories
                            state.mangaCategories.forEach { category ->
                                item {
                                    ContentSection(
                                        title = category.title,
                                        onViewAllClick = { onAction(HomeAction.OnViewAllMangaClick(category)) },
                                        items = category.items,
                                        onItemClick = { onAction(HomeAction.OnMangaClick(it)) }
                                    )
                                }
                            }
                        }

                        BottomNavItem.ANIME -> {
                            // All anime categories
                            state.animeCategories.forEach { category ->
                                item {
                                    ContentSection(
                                        title = category.title,
                                        onViewAllClick = { onAction(HomeAction.OnViewAllAnimeClick(category)) },
                                        items = category.items,
                                        onItemClick = { onAction(HomeAction.OnAnimeClick(it)) }
                                    )
                                }
                            }
                        }

                        BottomNavItem.MANGA -> {
                            // All manga categories
                            state.mangaCategories.forEach { category ->
                                item {
                                    ContentSection(
                                        title = category.title,
                                        onViewAllClick = { onAction(HomeAction.OnViewAllMangaClick(category)) },
                                        items = category.items,
                                        onItemClick = { onAction(HomeAction.OnMangaClick(it)) }
                                    )
                                }
                            }
                        }

                        BottomNavItem.EXPLORE -> {
                            // Genres, seasons, studios, etc.
                            item {
                                ExploreGrid(
                                    exploreCategories = state.exploreCategories,
                                    onCategoryClick = { onAction(HomeAction.OnExploreCategoryClick(it)) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    onSearchClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Anime & Manga Explorer",
                style = MaterialTheme.typography.headlineSmall
            )
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
            IconButton(onClick = onFavoritesClick) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorites")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ContentSection(
    title: String,
    onViewAllClick: () -> Unit,
    items: List<T>,
    onItemClick: (T) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            TextButton(onClick = onViewAllClick) {
                Text("View All")
            }
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items) { item ->
                ContentCard(
                    item = item,
                    onClick = { onItemClick(item) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ContentCard(
    item: T,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .width(140.dp)
            .height(210.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // This is a placeholder - you'll need to implement the actual display
        // of anime/manga items with images and titles based on your data model
        when (item) {
            is Anime -> {
                Column {
                    // Here you would normally use an AsyncImage or similar to load the cover
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(MaterialTheme.colorScheme.secondaryContainer)
                    )
                    item.title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
            is Manga -> {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                    )
                    item.title?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun FeaturedCarousel(
    featuredItems: List<FeaturedItem>,
    onAnimeClick: (AnimeTopDto) -> Unit,
    onMangaClick: (AnimeTopDto) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState { featuredItems.size }

    Column(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) { page ->
            val item = featuredItems[page]
            FeaturedItemCard(
                item = item,
                onClick = {
                    when (item) {
                        is FeaturedItem.AnimeFeatured -> onAnimeClick(item.anime)
                        is FeaturedItem.MangaFeatured -> onMangaClick(item.manga)
                    }
                }
            )
        }

//        HorizontalPagerIndicator(
//            pagerState = pagerState,
//            pageCount = featuredItems.size,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(16.dp),
//            activeColor = MaterialTheme.colorScheme.primary,
//            inactiveColor = MaterialTheme.colorScheme.surfaceVariant
//        )
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(featuredItems.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .background(color, shape = MaterialTheme.shapes.small)
                        .width(8.dp)
                        .height(8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeaturedItemCard(
    item: FeaturedItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Background image would go here with AsyncImage
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        when (item) {
                            is FeaturedItem.AnimeFeatured -> MaterialTheme.colorScheme.primaryContainer
                            is FeaturedItem.MangaFeatured -> MaterialTheme.colorScheme.secondaryContainer
                        }
                    )
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f))
                    .padding(16.dp)
            ) {
                val title = when (item) {
                    is FeaturedItem.AnimeFeatured -> item.anime.title
                    is FeaturedItem.MangaFeatured -> item.manga.title
                }

                val type = when (item) {
                    is FeaturedItem.AnimeFeatured -> "Anime"
                    is FeaturedItem.MangaFeatured -> "Manga"
                }

                if (title != null) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = type,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun ExploreGrid(
    exploreCategories: List<ExploreCategory>,
    onCategoryClick: (ExploreCategory) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        Text(
            text = "Explore",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(exploreCategories) { category ->
                ExploreCategoryCard(
                    category = category,
                    onClick = { onCategoryClick(category) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreCategoryCard(
    category: ExploreCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        modifier = modifier.aspectRatio(1.5f),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ErrorView(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = errorMessage,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

// Model classes for the Home screen
enum class BottomNavItem(val title: String, val icon: ImageVector) {
    HOME("Home", Icons.Default.Home),
    ANIME("Anime", Icons.Default.Home),
    MANGA("Manga", Icons.Default.Home),
    EXPLORE("Explore", Icons.Default.Home)
//    ANIME("Anime", Icons.Default.Movie),
//    MANGA("Manga", Icons.Default.Book),
//    EXPLORE("Explore", Icons.Default.Explore)
}

data class AnimeCategory(val id: String, val title: String, val items: List<AnimeTopDto>)
data class MangaCategory(val id: String, val title: String, val items: List<AnimeTopDto>)
data class ExploreCategory(val id: String, val name: String, val type: ExploreCategoryType)

enum class ExploreCategoryType {
    GENRE, SEASON, STUDIO, PUBLISHER
}

sealed class FeaturedItem {
    data class AnimeFeatured(val anime: AnimeTopDto) : FeaturedItem()
    data class MangaFeatured(val manga: AnimeTopDto) : FeaturedItem()
}