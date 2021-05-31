package com.rudyrachman16.core.domain.usecase

import com.rudyrachman16.core.data.Status
import com.rudyrachman16.core.domain.model.Meal
import com.rudyrachman16.core.domain.repository.IMealRepositories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class UseCase(private val mealRepositories: IMealRepositories) : IUseCase {
    override fun getRecommended(): Flow<Status<List<Meal>>> = mealRepositories.getRecommended()

    override fun getDetail(id: String): Flow<Status<Meal>> = mealRepositories.getDetail(id)

    override fun getSearch(state: StateFlow<String>) = mealRepositories.getSearch(state)

    override fun getFavorite(): Flow<List<Meal>> = mealRepositories.getFavorite()

    override suspend fun insertMeal(meal: Meal) = mealRepositories.insertMeal(meal)

    override suspend fun updateMeal(meal: Meal) = mealRepositories.updateMeal(meal)
}