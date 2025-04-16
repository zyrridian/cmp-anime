package com.zkylab.anime.anime.presentation.anime_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import cmp_anime.composeapp.generated.resources.Res
import cmp_anime.composeapp.generated.resources.anime_cover
import cmp_anime.composeapp.generated.resources.anime_error_2
import cmp_anime.composeapp.generated.resources.go_back
import cmp_anime.composeapp.generated.resources.mark_as_favorite
import cmp_anime.composeapp.generated.resources.remove_from_favorites
import coil3.compose.rememberAsyncImagePainter
import com.zkylab.anime.core.presentation.PulseAnimation
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun BlurredImageBackground(
    imageUrl: String?,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var imageLoadResult by remember {
        mutableStateOf<Result<Painter>?>(null)
    }

    val painter = rememberAsyncImagePainter(
        model = imageUrl,
        onSuccess = {
            val size = it.painter.intrinsicSize
            imageLoadResult = if (size.width > 1 && size.height > 1) {
                Result.success(it.painter)
            } else {
                Result.failure(Exception("Invalid image dimensions"))
            }
        },
        onError = {
            imageLoadResult = Result.failure(it.result.throwable)
        }
    )

    Box(modifier = modifier.fillMaxSize()) {
        // Background gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
        ) {
            // Blurred image background
            Image(
                painter = painter,
                contentDescription = stringResource(Res.string.anime_cover),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(16.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.3f))
            )

            // Gradient overlay for better text visibility
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                MaterialTheme.colorScheme.surface
                            ),
                            startY = 0f,
                            endY = 350f
                        )
                    )
            )
        }

        // Main content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Space for the header area
            Spacer(modifier = Modifier.height(200.dp))

            // Main content container
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {


                    // User-defined content
                    content()
                }
            }
        }

        // Cover image card
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier
                .fillMaxWidth()
                .offset(y = 80.dp)
        ) {
            ElevatedCard(
                modifier = Modifier
                    .height(230.dp)
                    .aspectRatio(2 / 3f),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 12.dp
                )
            ) {
                AnimatedContent(
                    targetState = imageLoadResult,
                    transitionSpec = {
                        fadeIn(tween(300)) togetherWith fadeOut(tween(300))
                    }
                ) { result ->
                    when (result) {
                        null -> Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        ) {
                            PulseAnimation(modifier = Modifier.size(60.dp))
                        }

                        else -> {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                // Main image
                                Image(
                                    painter = if (result.isSuccess) painter else {
                                        painterResource(Res.drawable.anime_error_2)
                                    },
                                    contentDescription = stringResource(Res.string.anime_cover),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .clip(RoundedCornerShape(16.dp)),
                                    contentScale = if (result.isSuccess) {
                                        ContentScale.Crop
                                    } else {
                                        ContentScale.Fit
                                    }
                                )

                                // Favorite button with improved styling
                                IconButton(
                                    onClick = onFavoriteClick,
                                    modifier = Modifier
                                        .align(Alignment.BottomEnd)
                                        .padding(8.dp)
                                        .shadow(4.dp, CircleShape)
                                        .background(
                                            color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                                            shape = CircleShape
                                        )
                                        .size(36.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isFavorite) {
                                            Icons.Filled.Favorite
                                        } else {
                                            Icons.Filled.FavoriteBorder
                                        },
                                        tint = if (isFavorite)
                                            MaterialTheme.colorScheme.error
                                        else
                                            MaterialTheme.colorScheme.onSurface,
                                        contentDescription = if (isFavorite) {
                                            stringResource(Res.string.remove_from_favorites)
                                        } else {
                                            stringResource(Res.string.mark_as_favorite)
                                        },
                                        modifier = Modifier.scale(0.8f)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Back button with improved visibility
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .statusBarsPadding()
                .padding(16.dp)
                .shadow(4.dp, CircleShape)
                .background(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                    shape = CircleShape
                )
                .size(40.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.go_back),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}