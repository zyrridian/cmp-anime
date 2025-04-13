package com.zkylab.anime.anime.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnimeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val malId: Int?,
    val title: String?,
    val imageUrl: String?,
    val synopsis: String?,
    val score: Double?,
    val type: String?,
    val rank: Int?,
    val popularity: Int?,
    val episodes: Int?,
    val duration: String?,
    val status: String?,
    val aired: String?,
    val studios: List<String>?,
    val genres: List<String>?
)
