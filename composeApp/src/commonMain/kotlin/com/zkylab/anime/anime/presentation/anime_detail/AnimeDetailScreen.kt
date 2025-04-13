package com.zkylab.anime.anime.presentation.anime_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_anime.composeapp.generated.resources.Res
import cmp_anime.composeapp.generated.resources.description_unavailable
import cmp_anime.composeapp.generated.resources.languages
import cmp_anime.composeapp.generated.resources.pages
import cmp_anime.composeapp.generated.resources.rating
import cmp_anime.composeapp.generated.resources.synopsis
import com.zkylab.anime.anime.presentation.anime_detail.components.BlurredImageBackground
import com.zkylab.anime.anime.presentation.anime_detail.components.AnimeChip
import com.zkylab.anime.anime.presentation.anime_detail.components.ChipSize
import com.zkylab.anime.anime.presentation.anime_detail.components.TitledContent
import com.zkylab.anime.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import kotlin.math.round

@Composable
fun AnimeDetailScreenRoot(
    viewModel: AnimeDetailViewModel,
    onBackClick: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    AnimeDetailScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is AnimeDetailAction.OnBackClick -> onBackClick()
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimeDetailScreen(
    state: AnimeDetailState,
    onAction: (AnimeDetailAction) -> Unit
) {
    BlurredImageBackground(
        imageUrl = state.anime?.imageUrl,
        isFavorite = state.isFavorite,
        onFavoriteClick = {
            onAction(AnimeDetailAction.OnFavoriteClick)
        },
        onBackClick = {
            onAction(AnimeDetailAction.OnBackClick)
        },
        modifier = Modifier.fillMaxSize()
    ) {
        if(state.anime != null) {
            Column(
                modifier = Modifier
                    .widthIn(max = 700.dp)
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 24.dp
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                state.anime.title?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                }
                state.anime.studios?.joinToString()?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.anime.score?.let { rating ->
                        TitledContent(
                            title = stringResource(Res.string.rating),
                        ) {
                            AnimeChip {
                                Text(
                                    text = "${round(rating * 10) / 10.0}"
                                )
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = SandYellow
                                )
                            }
                        }
                    }
                    state.anime.duration?.let { pageCount ->
                        TitledContent(
                            title = stringResource(Res.string.pages),
                        ) {
                            AnimeChip {
                                Text(text = pageCount.toString())
                            }
                        }
                    }
                }
                if(state.anime.genres?.isNotEmpty() == true) {
                    TitledContent(
                        title = stringResource(Res.string.languages),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    ) {
                        FlowRow(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.wrapContentSize(Alignment.Center)
                        ) {
                            state.anime.genres.forEach { language ->
                                AnimeChip(
                                    size = ChipSize.SMALL,
                                    modifier = Modifier.padding(2.dp)
                                ) {
                                    Text(
                                        text = language.uppercase(),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .fillMaxWidth()
                        .padding(
                            top = 24.dp,
                            bottom = 8.dp
                        )
                )
                if(state.isLoading) {
                    CircularProgressIndicator()
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .weight(1f),
//                        contentAlignment = Alignment.Center
//                    ) {
//                    }
                } else {
                    Text(
                        text = if(state.anime.synopsis.isNullOrBlank()) {
                            stringResource(Res.string.description_unavailable)
                        } else {
                            state.anime.synopsis
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Justify,
                        color = if(state.anime.synopsis.isNullOrBlank()) {
                            Color.Black.copy(alpha = 0.4f)
                        } else Color.Black,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}