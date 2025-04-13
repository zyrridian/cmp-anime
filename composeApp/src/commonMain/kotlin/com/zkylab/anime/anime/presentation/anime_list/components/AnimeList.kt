package com.zkylab.anime.anime.presentation.anime_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zkylab.anime.anime.domain.Anime

@Composable
fun AnimeList(
    anime: List<Anime>,
    onAnimeClick: (Anime) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState(),
    onLoadMore: () -> Unit = {},
    isLoadingMore: Boolean = false
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(anime.size) { index ->
            val item = anime[index]
            AnimeListItem(
                anime = item,
                modifier = Modifier.widthIn(max = 700.dp).fillMaxWidth().padding(horizontal = 16.dp),
                onClick = { onAnimeClick(item) }
            )

            if (index == anime.lastIndex) {
                LaunchedEffect(Unit) {
                    onLoadMore()
                }
            }
        }

        if (isLoadingMore) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}
