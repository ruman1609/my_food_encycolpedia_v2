package com.rudyrachman16.core.domain.usecase

import com.rudyrachman16.core.data.Status
import com.rudyrachman16.core.domain.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface IUseCase {
    fun getRecommended(): Flow<Status<List<Meal>>>
    fun getDetail(id: String): Flow<Status<Meal>>
    fun getSearch(state: StateFlow<String>): Flow<List<Meal?>?>
    fun getFavorite(): Flow<List<Meal>>

    suspend fun insertMeal(meal: Meal)

    suspend fun updateMeal(meal: Meal)
}