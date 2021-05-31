package com.rudyrachman16.core.data.api.response

import androidx.annotation.Keep

@Keep
data class ListResponse(
    val meals: List<MealResponse>?
)

