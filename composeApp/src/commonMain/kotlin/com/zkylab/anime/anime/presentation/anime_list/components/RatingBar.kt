package com.zkylab.anime.anime.presentation.anime_list.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    rating: Float,
    maxRating: Float = 10f,
    starSize: Dp = 16.dp,
    color: Color = MaterialTheme.colorScheme.tertiary
) {
    val filledStars = (rating / (maxRating / 5)).coerceIn(0f, 5f)
    val filledStarsInt = filledStars.toInt()
    val partialFill = filledStars - filledStarsInt

    Row {
        repeat(filledStarsInt) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(starSize)
            )
        }

        if (partialFill > 0) {
            Box(modifier = Modifier.size(starSize)) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = color.copy(alpha = 0.3f)
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier
                        .fillMaxWidth(partialFill)
                        .clip(RectangleShape)
                )
            }
        }

        repeat(5 - filledStarsInt - if (partialFill > 0) 1 else 0) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = color.copy(alpha = 0.3f),
                modifier = Modifier.size(starSize)
            )
        }
    }
}