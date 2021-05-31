package com.rudyrachman16.core.utils

import android.content.Context

object GridBoxCount {
    private const val MARGIN_SPACE = 4
    private const val WIDTH = 150
    fun countBox(context: Context): Int {
        val display = context.resources.displayMetrics
        val screenWidth: Float = display.widthPixels / display.density
        return (screenWidth / (WIDTH + MARGIN_SPACE * 2) + 0.5).toInt()
    }
}