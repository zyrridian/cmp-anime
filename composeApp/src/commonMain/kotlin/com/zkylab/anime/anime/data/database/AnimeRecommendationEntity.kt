package com.zkylab.anime.anime.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_recommendations")
data class AnimeRecommendationEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // for local database ID
    val malId: Int?,
    val title: String?,
    val imageUrl: String?, // you can store JPG's `image_url`
    val url: String?,
    val votes: Int?
)