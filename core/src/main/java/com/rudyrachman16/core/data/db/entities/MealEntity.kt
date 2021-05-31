package com.rudyrachman16.core.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey
    val idMeal: String,
    val strMeal: String,
    val strMealThumb: String,
    val strCategory: String? = null,
    val strArea: String? = null,
    val strInstructions: String? = null,
    val strDrinkAlternate: String? = null,
    val strYoutube: String? = null,
    val strSource: String? = null,
    val ingredients: String? = null,
    val measurements: String? = null,
    var favorite: Boolean?
)
