package com.rudyrachman16.myfoodencyclopedia.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.rudyrachman16.core.adapters.GridAdapter
import com.rudyrachman16.core.utils.GridBoxCount
import com.rudyrachman16.myfoodencyclopedia.detail.DetailActivity
import com.rudyrachman16.myfoodencyclopedia.favorite.databinding.ActivityFavoriteBinding
import org.koin.android.ext.android.inject
import org.koin.core.context.loadKoinModules

class FavoriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: FavoriteViewModel by inject()
        val bind = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(bind.root)

        setSupportActionBar(bind.favToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadKoinModules(favoriteModule)

        title = "Favorite Meal"

        val adapter = GridAdapter({ bind.favEmpty.visibility = View.VISIBLE }) {
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra(DetailActivity.ID_MEAL, it.idMeal)
                putExtra(DetailActivity.MEAL_NAME, it.strMeal)
            }
            startActivity(intent)
        }
        bind.favRV.apply {
            setHasFixedSize(true)
            this.adapter = adapter
            layoutManager =
                GridLayoutManager(applicationContext, GridBoxCount.countBox(applicationContext))
        }

        bind.loadingFav.visibility = View.VISIBLE

        viewModel.getFavorite.observe(this) {
            bind.loadingFav.visibility = View.GONE
            adapter.setList(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}