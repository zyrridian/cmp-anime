package com.zkylab.anime.anime.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteAnimeDao {
    @Upsert
    suspend fun upsert(book: AnimeEntity)

    @Query("SELECT * FROM AnimeEntity")
    fun getFavoriteAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM AnimeEntity WHERE malId = :id")
    suspend fun getFavoriteAnime(id: String): AnimeEntity?

    @Query("DELETE FROM AnimeEntity WHERE malId = :id")
    suspend fun deleteFavoriteAnime(id: String)
}