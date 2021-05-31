package com.rudyrachman16.core.utils

import java.util.*

object CategorySetter {
    @JvmStatic
    private var category: String? = null

    fun getCategory(): String = category ?: synchronized(this) {
        val categories =
            arrayOf("Beef", "Breakfast", "Chicken", "Dessert", "Pasta", "Seafood", "Side")
        val index = getDayToIndex()
        category ?: categories[index].apply { category = this }
    }

    private fun getDayToIndex(): Int {
        val calendar = Calendar.getInstance()
        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> 0
            Calendar.MONDAY -> 1
            Calendar.TUESDAY -> 2
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 4
            Calendar.FRIDAY -> 5
            Calendar.SATURDAY -> 6
            else -> -1
        }
    }
}