package com.rudyrachman16.core.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rudyrachman16.core.R
import com.rudyrachman16.core.databinding.PerItemBinding
import com.rudyrachman16.core.domain.model.Meal

class GridAdapter(private val emptyList: (() -> Unit), private val clickListener: (Meal) -> Unit) :
    RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val bind = PerItemBinding.bind(view)

        fun bind(meal: Meal) {
            itemView.setOnClickListener { clickListener(meal) }
            bind.strMeal.text = meal.strMeal
            Glide.with(itemView.context).load(meal.strMealThumb)
                .apply(RequestOptions.overrideOf(150))
                .apply(RequestOptions.placeholderOf(R.drawable.loading))
                .error(R.drawable.ic_broken).into(bind.mealThumb)
        }
    }

    private val meals = ArrayList<Meal>()
    fun setList(list: List<Meal>?) {
        if (list == null || list.isEmpty()) {
            emptyList()
            clearList()
            return
        }
        meals.clear()
        meals.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        meals.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.per_item, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(meals[position])
    }

    override fun getItemCount(): Int = meals.size
}