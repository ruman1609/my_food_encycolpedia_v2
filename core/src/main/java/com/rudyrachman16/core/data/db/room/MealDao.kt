package com.rudyrachman16.core.data.db.room

import androidx.room.*
import com.rudyrachman16.core.data.db.entities.MealEntity
import com.rudyrachman16.core.utils.CategorySetter
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meal WHERE strCategory = :category ORDER BY strMeal ASC")
    fun getRecommended(category: String = CategorySetter.getCategory()): Flow<List<MealEntity>>

    @Query("SELECT * FROM meal WHERE idMeal = :id")
    fun getDetail(id: String): Flow<MealEntity>

    @Query("SELECT * FROM meal WHERE idMeal = :id")
    fun getDetailOrNull(id: String): Flow<MealEntity?>

    @Query("SELECT * FROM meal WHERE favorite = 1")
    fun getFavorite(): Flow<List<MealEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertToDB(list: List<MealEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToDB(meal: MealEntity)

    @Update
    suspend fun updateMeal(meal: MealEntity)
}