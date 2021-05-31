package com.rudyrachman16.core.utils

import com.rudyrachman16.core.data.api.response.MealResponse
import com.rudyrachman16.core.data.db.entities.MealEntity
import com.rudyrachman16.core.domain.model.Meal

object MapVal {
    private fun check(str: String?): String = if (str != null && str != "") ",$str" else ""

    fun mealResToEnt(meal: MealResponse, res: MealEntity?): MealEntity {
        val ingredients =
            "${meal.strIngredient1 ?: ""}${check(meal.strIngredient2)}${check(meal.strIngredient3)}" +
                    "${check(meal.strIngredient4)}${check(meal.strIngredient5)}${check(meal.strIngredient6)}" +
                    "${check(meal.strIngredient7)}${check(meal.strIngredient8)}${check(meal.strIngredient9)}" +
                    "${check(meal.strIngredient10)}${check(meal.strIngredient11)}${check(meal.strIngredient12)}" +
                    "${check(meal.strIngredient13)}${check(meal.strIngredient14)}${check(meal.strIngredient15)}" +
                    "${check(meal.strIngredient16)}${check(meal.strIngredient17)}${check(meal.strIngredient18)}" +
                    "${check(meal.strIngredient19)}${check(meal.strIngredient20)}"

        val measurements =
            "${meal.strMeasure1 ?: ""}${check(meal.strMeasure2)}${check(meal.strMeasure3)}" +
                    "${check(meal.strMeasure4)}${check(meal.strMeasure5)}${check(meal.strMeasure6)}" +
                    "${check(meal.strMeasure7)}${check(meal.strMeasure8)}${check(meal.strMeasure9)}" +
                    "${check(meal.strMeasure10)}${check(meal.strMeasure11)}${check(meal.strMeasure12)}" +
                    "${check(meal.strMeasure13)}${check(meal.strMeasure14)}${check(meal.strMeasure15)}" +
                    "${check(meal.strMeasure16)}${check(meal.strMeasure17)}${check(meal.strMeasure18)}" +
                    "${check(meal.strMeasure19)}${check(meal.strMeasure20)}"
        val fav = res?.favorite ?: false
        return MealEntity(
            meal.idMeal!!, meal.strMeal!!, meal.strMealThumb!!,
            meal.strCategory ?: CategorySetter.getCategory(), meal.strArea,
            meal.strInstructions, meal.strDrinkAlternate, meal.strYoutube, meal.strSource,
            ingredients, measurements, fav
        )
    }

    fun mealResToEnt(meal: List<MealResponse>, res: List<MealEntity>?): List<MealEntity> =
        meal.mapIndexed { index, it ->
            mealResToEnt(it, res?.getOrNull(index))
        }

    fun mealEntToDom(mealEnt: MealEntity): Meal {
        val ingredients: List<String>? = mealEnt.ingredients?.split(",")?.toList()
        val measurements: List<String>? = mealEnt.measurements?.split(",")?.toList()
        return Meal(
            mealEnt.idMeal, mealEnt.strMeal, mealEnt.strMealThumb, mealEnt.strCategory,
            mealEnt.strArea, mealEnt.strInstructions, mealEnt.strDrinkAlternate, mealEnt.strYoutube,
            mealEnt.strSource, ingredients, measurements, mealEnt.favorite!!
        )
    }

    fun mealEntToDom(meal: List<MealEntity>): List<Meal> = meal.map { mealEnt ->
        mealEntToDom(mealEnt)
    }

    fun mealDomToEnt(meal: Meal): MealEntity {
        val ingredients = meal.ingredients?.toString()
            ?.replace("[", "")?.replace("]", "")?.replace(", ", ",")
        val measurements = meal.measurements?.toString()
            ?.replace("[", "")?.replace("]", "")?.replace(", ", ",")
        return MealEntity(
            meal.idMeal, meal.strMeal, meal.strMealThumb, meal.strCategory, meal.strArea,
            meal.strInstructions, meal.strDrinkAlternate, meal.strYoutube, meal.strSource,
            ingredients, measurements, meal.favorite
        )
    }


    fun mealDomToEnt(meal: List<Meal>?): List<MealEntity>? = meal?.map {
        mealDomToEnt(it)
    }
}