package com.zkylab.anime.app


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zkylab.anime.anime.presentation.home.AnimeCategory
import com.zkylab.anime.anime.presentation.home.BottomNavItem
import com.zkylab.anime.anime.presentation.home.ExploreCategory
import com.zkylab.anime.anime.presentation.home.FeaturedItem
import com.zkylab.anime.anime.presentation.home.MangaCategory
import com.zkylab.anime.core.presentation.UiText
//import com.zkylab.anime.manga.domain.Manga
import org.koin.compose.viewmodel.koinViewModel



