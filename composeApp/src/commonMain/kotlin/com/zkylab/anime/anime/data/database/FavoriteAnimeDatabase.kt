package com.zkylab.anime.anime.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [AnimeEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(AnimeDatabaseConstructor::class)
abstract class FavoriteAnimeDatabase: RoomDatabase() {
    abstract val favoriteAnimeDao: FavoriteAnimeDao

    companion object {
        const val DB_NAME = "anime.db"
    }
}