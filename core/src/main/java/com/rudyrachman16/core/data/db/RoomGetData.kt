package com.rudyrachman16.core.data.db

import com.rudyrachman16.core.data.db.entities.MealEntity
import com.rudyrachman16.core.data.db.room.MealDao
import kotlinx.coroutines.flow.Flow

class RoomGetData(private val dao: MealDao) {
    fun getRecommended(): Flow<List<MealEntity>> = dao.getRecommended()

    fun getDetail(id: String): Flow<MealEntity> = dao.getDetail(id)

    fun getDetailOrNull(id: String): Flow<MealEntity?> = dao.getDetailOrNull(id)

    fun getFavorite(): Flow<List<MealEntity>> = dao.getFavorite()

    suspend fun insertToDB(list: List<MealEntity>) = dao.insertToDB(list)

    suspend fun insertToDB(meal: MealEntity) = dao.insertToDB(meal)

    suspend fun updateMeal(meal: MealEntity) {
        meal.favorite = !meal.favorite!!
        dao.updateMeal(meal)
    }
}