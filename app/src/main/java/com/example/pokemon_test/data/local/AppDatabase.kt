package com.example.pokemon_test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.pokemon_test.domain.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}