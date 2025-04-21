package com.zkylab.anime


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.zkylab.anime.anime.presentation.anime_list.AnimeListScreen
import com.zkylab.anime.anime.presentation.anime_list.AnimeListState
import com.zkylab.anime.anime.presentation.anime_list.components.AnimeSearchBar

@Preview
@Composable
private fun BookSearchBarPreview() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        AnimeSearchBar(
            searchQuery = "",
            onSearchQueryChange = {},
            onImeSearch = {},
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

//private val anime = (1..100).map {
//    Anime(
//        malId = 1,
//        title = "1",
//        imageUrl = "1",
//        synopsis = "TODO()",
//        score = 1.2,
//        type = "a",
//        rank = 1,
//        popularity = 1,
//        episodes = 1,
//        duration = "a",
//        status = "a",
//        aired = "a",
//        studios = listOf("", ""),
//        genres = listOf("", "")
//    )
//}

//@Preview
//@Composable
//private fun BookListScreenPreview() {
//    AnimeListScreen(
//        state = AnimeListState(
//            searchResults = anime
//        ),
//        onAction = {}
//    )
//}