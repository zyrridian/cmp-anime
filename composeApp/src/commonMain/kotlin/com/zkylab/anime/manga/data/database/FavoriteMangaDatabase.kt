package com.zkylab.anime.manga.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MangaEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(MangaDatabaseConstructor::class)
abstract class FavoriteMangaDatabase: RoomDatabase() {
    abstract val favoriteMangaDao: FavoriteMangaDao

    companion object {
        const val DB_NAME = "manga.db"
    }
}