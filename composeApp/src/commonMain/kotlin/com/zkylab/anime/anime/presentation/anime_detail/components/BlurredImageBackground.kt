package com.zkylab.anime.anime.presentation.anime_detail.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import com.zkylab.anime.core.presentation.DarkBlue
import com.zkylab.anime.core.presentation.DesertWhite
import com.zkylab.anime.core.presentation.PulseAnimation
import com.zkylab.anime.core.presentation.SandYellow
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
            it.result.throwable.printStackTrace()
        }
    )

    Box(modifier = modifier.fillMaxSize()) {

        // 🔹 Static blurred image background with hardcoded height
        Image(
            painter = painter,
            contentDescription = stringResource(Res.string.anime_cover),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .blur(20.dp)
                .background(DarkBlue)
        )

        // 🔹 Scrollable foreground content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Spacer to push content below the background image
            Spacer(modifier = Modifier.height(200.dp)) // adjust to overlap partially

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DesertWhite)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ElevatedCard(
                        modifier = Modifier
                            .height(230.dp)
                            .aspectRatio(2 / 3f)
                            .offset(y = (-115).dp),
                        shape = RoundedCornerShape(8.dp),
                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 15.dp
                        )
                    ) {
                        AnimatedContent(targetState = imageLoadResult) { result ->
                            when (result) {
                                null -> Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    PulseAnimation(modifier = Modifier.size(60.dp))
                                }

                                else -> {
                                    Box {
                                        Image(
                                            painter = if (result.isSuccess) painter else {
                                                painterResource(Res.drawable.anime_error_2)
                                            },
                                            contentDescription = stringResource(Res.string.anime_cover),
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .background(Color.Transparent),
                                            contentScale = if (result.isSuccess) {
                                                ContentScale.Crop
                                            } else {
                                                ContentScale.Fit
                                            }
                                        )

                                        IconButton(
                                            onClick = onFavoriteClick,
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                                .background(
                                                    brush = Brush.radialGradient(
                                                        colors = listOf(
                                                            SandYellow, Color.Transparent
                                                        ),
                                                        radius = 70f
                                                    )
                                                )
                                        ) {
                                            Icon(
                                                imageVector = if (isFavorite) {
                                                    Icons.Filled.Favorite
                                                } else {
                                                    Icons.Outlined.FavoriteBorder
                                                },
                                                tint = Color.Red,
                                                contentDescription = if (isFavorite) {
                                                    stringResource(Res.string.remove_from_favorites)
                                                } else {
                                                    stringResource(Res.string.mark_as_favorite)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Call user-defined content below card
                    content()
                }
            }
        }

        // 🔹 Back button on top
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 16.dp, start = 16.dp)
                .statusBarsPadding()
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(Res.string.go_back),
                tint = Color.White
            )
        }
    }
}
