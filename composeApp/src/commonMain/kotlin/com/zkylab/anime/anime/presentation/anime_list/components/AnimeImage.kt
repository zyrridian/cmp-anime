package com.zkylab.anime.anime.presentation.anime_list.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_anime.composeapp.generated.resources.Res
import cmp_anime.composeapp.generated.resources.anime_error_2
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource

@Composable
fun AnimeImage(
    imageUrl: String?,
    title: String,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        var imageLoadResult by remember {
            mutableStateOf<Result<Painter>?>(null)
        }

        val painter = rememberAsyncImagePainter(
            model = imageUrl,
            onSuccess = {
                imageLoadResult =
                    if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                        Result.success(it.painter)
                    } else {
                        Result.failure(Exception("Invalid image size"))
                    }
            },
            onError = {
                imageLoadResult = Result.failure(it.result.throwable)
            }
        )

        val painterState by painter.state.collectAsStateWithLifecycle()
        val transition by animateFloatAsState(
            targetValue = if (painterState is AsyncImagePainter.State.Success) 1f else 0f,
            animationSpec = tween(durationMillis = 500),
            label = "image transition"
        )

        when (val result = imageLoadResult) {
            null -> {
                // Shimmer loading animation
                Box(
                    modifier = Modifier
                        .aspectRatio(0.65f)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                                ),
//                                animateFloat = true
                            )
                        )
                )
            }
            else -> {
                Image(
                    painter = if (result.isSuccess) painter else painterResource(Res.drawable.anime_error_2),
                    contentDescription = title,
                    contentScale = if (result.isSuccess) ContentScale.Crop else ContentScale.Fit,
                    modifier = Modifier
                        .aspectRatio(0.65f)
                        .graphicsLayer {
                            alpha = transition
                            scaleX = 0.8f + (0.2f * transition)
                            scaleY = 0.8f + (0.2f * transition)
                        }
                )
            }
        }
    }
}
