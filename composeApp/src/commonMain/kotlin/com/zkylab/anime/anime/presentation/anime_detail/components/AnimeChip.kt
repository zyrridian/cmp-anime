package com.zkylab.anime.anime.presentation.anime_detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class ChipSize {
    SMALL, REGULAR
}

@Composable
fun AnimeChip(
    modifier: Modifier = Modifier,
    size: ChipSize = ChipSize.REGULAR,
    chipContent: @Composable RowScope.() -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .widthIn(
                min = when(size) {
                    ChipSize.SMALL -> 50.dp
                    ChipSize.REGULAR -> 80.dp
                }
            )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(
                vertical = when(size) {
                    ChipSize.SMALL -> 6.dp
                    ChipSize.REGULAR -> 8.dp
                },
                horizontal = when(size) {
                    ChipSize.SMALL -> 10.dp
                    ChipSize.REGULAR -> 12.dp
                }
            )
        ) {
            chipContent()
        }
    }
}