package com.zkylab.anime.manga.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMangaDao {
    @Upsert
    suspend fun upsert(book: MangaEntity)

    @Query("SELECT * FROM MangaEntity")
    fun getFavoriteManga(): Flow<List<MangaEntity>>

    @Query("SELECT * FROM MangaEntity WHERE malId = :id")
    suspend fun getFavoriteManga(id: String): MangaEntity?

    @Query("DELETE FROM MangaEntity WHERE malId = :id")
    suspend fun deleteFavoriteManga(id: String)
}