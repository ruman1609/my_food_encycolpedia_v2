package com.rudyrachman16.core.data.api

import android.util.Log
import com.rudyrachman16.core.data.api.response.MealResponse
import com.rudyrachman16.core.data.api.retrofit.ApiService
import com.rudyrachman16.core.data.api.retrofit.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RetrofitGetData(private val apiService: ApiService) {
    suspend fun getCategories(): Flow<ApiStatus<List<MealResponse>>> = flow {
        try {
            val response = apiService.getCategories()
            val list = response.meals!!
            if (list.isNotEmpty()) emit(ApiStatus.Success(list))
            else emit(ApiStatus.Empty)
        } catch (e: Exception) {
            emit(ApiStatus.Failed(e.toString()))
            Log.d("RetrofitGetData", e.toString())
        }
    }.flowOn(Dispatchers.IO)

    suspend fun getSearch(query: String) = apiService.getSearch(query)

    suspend fun getDetail(id: String): Flow<ApiStatus<MealResponse>> = flow {
        try {
            val response = apiService.getDetail(id)
            val meal = response.meals!![0]
            emit(ApiStatus.Success(meal))
        } catch (e: Exception) {
            emit(ApiStatus.Failed(e.toString()))
            Log.d("RetrofitGetData", e.toString())
        }
    }.flowOn(Dispatchers.IO)
}