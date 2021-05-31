package com.rudyrachman16.core.data.api.retrofit

import com.rudyrachman16.core.data.api.response.ListResponse
import com.rudyrachman16.core.utils.CategorySetter
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("filter.php")
    suspend fun getCategories(
        @Query("c") category: String = CategorySetter.getCategory()
    ): ListResponse

    @GET("search.php")
    suspend fun getSearch(@Query("s") search: String): ListResponse

    @GET("lookup.php")
    suspend fun getDetail(@Query("i") search: String): ListResponse
}