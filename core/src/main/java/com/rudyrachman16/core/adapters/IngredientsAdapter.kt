package com.rudyrachman16.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rudyrachman16.core.BuildConfig
import com.rudyrachman16.core.R
import com.rudyrachman16.core.databinding.PerIngredientsBinding

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bind = PerIngredientsBinding.bind(view)
        fun binding(ingredient: String, measurement: String) {
            bind.ingredient.text = ingredient
            bind.measurement.text = measurement
            Glide.with(itemView.context)
                .load("${BuildConfig.INGREDIENT_URL}$ingredient-Small.png")
                .apply(RequestOptions.overrideOf(100))
                .apply(RequestOptions.placeholderOf(R.drawable.loading))
                .error(R.drawable.ic_broken).into(bind.ingredientsThumb)
        }
    }

    private val ingredients = ArrayList<String>()
    private val measurements = ArrayList<String>()

    fun setList(listIng: List<String>?, listMea: List<String>?) {
        if (listIng == null || listMea == null || listIng.isEmpty() || listMea.isEmpty()) return
        ingredients.clear()
        measurements.clear()
        ingredients.addAll(listIng)
        measurements.addAll(listMea)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.per_ingredients, parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding(ingredients[position], measurements[position])
    }

    override fun getItemCount(): Int = ingredients.size
}