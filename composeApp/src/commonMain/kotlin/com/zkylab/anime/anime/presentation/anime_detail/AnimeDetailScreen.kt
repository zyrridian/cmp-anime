package com.zkylab.anime.anime.presentation.anime_detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_anime.composeapp.generated.resources.Res
import cmp_anime.composeapp.generated.resources.description_unavailable
import cmp_anime.composeapp.generated.resources.genre
import cmp_anime.composeapp.generated.resources.duration
import cmp_anime.composeapp.generated.resources.rating
import cmp_anime.composeapp.generated.resources.synopsis
import com.zkylab.anime.anime.presentation.anime_detail.components.BlurredImageBackground
import com.zkylab.anime.anime.presentation.anime_detail.components.AnimeChip
import com.zkylab.anime.anime.presentation.anime_detail.components.CharacterCard
import com.zkylab.anime.anime.presentation.anime_detail.components.ChipSize
import com.zkylab.anime.anime.presentation.anime_detail.components.RecommendationCard
import com.zkylab.anime.anime.presentation.anime_detail.components.StaffCard
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
            when (action) {
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
    Box(modifier = Modifier.fillMaxSize()
        .verticalScroll(rememberScrollState())) {
        if (state.isLoading && state.anime == null) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
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
                state.anime?.let { anime ->
                    AnimeDetailContent(
                        state = state,
                        onAction = onAction
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun AnimeDetailContent(
    state: AnimeDetailState,
    onAction: (AnimeDetailAction) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .widthIn(max = 700.dp)
            .fillMaxWidth()
            .padding(bottom = 24.dp)
    ) {

        Spacer(modifier = Modifier.height(124.dp))

        // Title and Studio Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            state.anime?.title?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            state.anime?.studios?.joinToString()?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Info Section (Rating and Duration)
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 8.dp)
            ) {
                state.anime?.score?.let { rating ->
                    TitledContent(
                        title = stringResource(Res.string.rating),
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "${round(rating * 10) / 10.0}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = SandYellow,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }

                state.anime?.duration?.let { duration ->
                    TitledContent(
                        title = stringResource(Res.string.duration),
                    ) {
                        Text(
                            text = "$duration min",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Genres Section
        if (state.anime?.genres?.isNotEmpty() == true) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
                ),
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                TitledContent(
                    title = stringResource(Res.string.genre),
                    modifier = Modifier.padding(vertical = 12.dp, horizontal = 16.dp)
                ) {
                    FlowRow(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        state.anime.genres.forEach { genre ->
                            AnimeChip(
                                size = ChipSize.SMALL,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Text(
                                    text = genre,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }

        // Synopsis Section
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
            ),
            modifier = Modifier
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = stringResource(Res.string.synopsis),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (state.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                } else {
                    Text(
                        text = if (state.anime?.synopsis.isNullOrBlank()) {
                            stringResource(Res.string.description_unavailable)
                        } else {
                            state.anime?.synopsis.orEmpty()
                        },
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Justify,
                        color = if (state.anime?.synopsis.isNullOrBlank()) {
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        } else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }

        // Characters Section
        SectionTitle(
            title = "Characters",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
        )

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            state.characters?.let { characters ->
                if (characters.isNotEmpty()) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        items(characters) { character ->
                            val voiceActor = character.voiceActors.firstOrNull()
                            CharacterCard(
                                characterName = character.name,
                                characterImageUrl = character.imageUrl,
                                role = character.role,
                                voiceActorName = voiceActor?.name,
                                voiceActorImageUrl = voiceActor?.imageUrl
                            )
                        }
                    }
                } else {
                    EmptySection(text = "No characters available")
                }
            } ?: EmptySection(text = "No characters available")
        }

        if (state.isLoading) {
            LoadingSection()
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        // Staff Section
        SectionTitle(
            title = "Staff",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
        )

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            state.staff?.let { staffList ->
                if (staffList.isNotEmpty()) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        items(staffList) { staff ->
                            StaffCard(
                                staffName = staff.name,
                                staffImageUrl = staff.imageUrl,
                                positions = staff.positions,
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                    }
                } else {
                    EmptySection(text = "No staff available")
                }
            } ?: EmptySection(text = "No staff available")
        }

        if (state.isLoading) {
            LoadingSection()
        }

        HorizontalDivider(
            color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
        )

        // Recommendations Section
        SectionTitle(
            title = "Recommendations",
            modifier = Modifier
                .align(Alignment.Start)
                .padding(horizontal = 24.dp)
        )

        AnimatedVisibility(
            visible = !state.isLoading,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            state.recommendations?.let { recommendations ->
                if (recommendations.isNotEmpty()) {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        items(recommendations) { recommendation ->
                            RecommendationCard(
                                title = recommendation.title,
                                imageUrl = recommendation.imageUrl ?: "",
                                modifier = Modifier
                            )
                        }
                    }
                } else {
                    EmptySection(text = "No recommendations available")
                }
            } ?: EmptySection(text = "No recommendations available")
        }

        if (state.isLoading) {
            LoadingSection()
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        modifier = modifier.padding(top = 24.dp, bottom = 8.dp)
    )
}

@Composable
private fun EmptySection(text: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
    }
}

@Composable
private fun LoadingSection() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(32.dp)
        )
    }
}