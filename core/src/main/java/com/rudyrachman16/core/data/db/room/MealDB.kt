package com.rudyrachman16.core.data.db.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rudyrachman16.core.data.db.entities.MealEntity

@Database(entities = [MealEntity::class], version = 1, exportSchema = false)
abstract class MealDB : RoomDatabase() {
    abstract fun getDao(): MealDao
}