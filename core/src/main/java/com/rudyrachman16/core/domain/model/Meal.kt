package com.rudyrachman16.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strDrinkAlternate: String? = null,
    val strYoutube: String? = null,
    val strSource: String? = null,
    val ingredients: List<String>? = null,
    val measurements: List<String>? = null,
    var favorite: Boolean = false
) : Parcelable