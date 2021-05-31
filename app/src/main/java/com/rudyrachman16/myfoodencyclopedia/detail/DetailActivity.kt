package com.rudyrachman16.myfoodencyclopedia.detail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.rudyrachman16.core.adapters.IngredientsAdapter
import com.rudyrachman16.core.data.Status
import com.rudyrachman16.core.domain.model.Meal
import com.rudyrachman16.myfoodencyclopedia.R
import com.rudyrachman16.myfoodencyclopedia.databinding.ActivityDetailBinding
import org.koin.android.ext.android.inject

class DetailActivity : AppCompatActivity() {
    private var binding: ActivityDetailBinding? = null
    private val bind get() = binding!!
    private val viewModel: DetailViewModel by inject()

    companion object {
        private const val MEAL = "meal"
        const val ID_MEAL = "idMeal"
        const val MEAL_NAME = "strMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setSupportActionBar(bind.detToolbar.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val observer = Observer<Status<Meal>> {
            when (it) {
                is Status.Success -> bindView(it.data)
                is Status.Loading -> loadingShow(true)
                is Status.Error -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.error_status, it.error),
                        Toast.LENGTH_LONG
                    ).show()
                    bindView(it.data)
                }
            }
        }

        if (intent.extras?.getParcelable<Meal>(MEAL) != null) {
            val meal = intent.extras?.getParcelable<Meal>(MEAL)!!
            viewModel.insertMeal(meal)
            title = meal.strMeal
            loadingShow(true)
            viewModel.getDetail(meal.idMeal).observe(this, observer)
        } else {
            title = intent.extras!!.getString(MEAL_NAME, "")
            viewModel.getDetail(intent.extras!!.getString(ID_MEAL, "")).observe(this, observer)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadingShow(status: Boolean) {
        bind.detLoad.visibility = if (status) View.VISIBLE else View.GONE
        bind.detContent.visibility = if (status) View.GONE else View.VISIBLE
        bind.detFavoriteFAB.visibility = if (status) View.GONE else View.VISIBLE
    }

    private fun bindView(meal: Meal?) {
        if (meal == null) return
        loadingShow(false)
        Glide.with(applicationContext).load(meal.strMealThumb)
            .apply(RequestOptions.placeholderOf(com.rudyrachman16.core.R.drawable.loading))
            .error(com.rudyrachman16.core.R.drawable.ic_broken).into(bind.detMealThumb)
        bind.detStrMeal.text = meal.strMeal
        bind.detArea.text = meal.strArea
        bind.detCategory.text = meal.strCategory
        initIngredients(meal.ingredients, meal.measurements)
        val text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            Html.fromHtml(
                "<ul><li>${meal.strInstructions?.replace("\n", "<li>")}</ul>",
                Html.FROM_HTML_MODE_LEGACY
            )
        else
            meal.strInstructions?.replace("\n", "\n\n")
        bind.detInstruction.text = text
        initYtb(meal.strYoutube)

        if (meal.strDrinkAlternate == null || meal.strDrinkAlternate == "")
            bind.detDrink.visibility = View.GONE
        else bind.detDrink.text =
            getString(R.string.you_can_make_s_for_the_drink, meal.strDrinkAlternate)

        if (meal.strSource == null || meal.strSource == "") bind.detSource.visibility = View.GONE
        else {
            bind.detSource.text = getString(R.string.source_s, meal.strSource)
            bind.detSource.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(meal.strSource)
                startActivity(intent)
            }
        }

        changeIcon(meal.favorite)
        bind.detFavoriteFAB.setOnClickListener { setFavorite(meal) }
    }

    private fun setFavorite(meal: Meal) {
        changeIcon(!meal.favorite)
        viewModel.setFavorite(meal)
    }

    private fun changeIcon(favorite: Boolean) {
        if (favorite) bind.detFavoriteFAB.setImageDrawable(
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_favorite)
        )
        else bind.detFavoriteFAB.setImageDrawable(
            ContextCompat.getDrawable(applicationContext, R.drawable.ic_unfavorite)
        )
    }

    private fun initYtb(youtubeURL: String?) {
        if (youtubeURL == null) {
            bind.youtubePlayerView.visibility = View.GONE
            return
        }
        lifecycle.addObserver(bind.youtubePlayerView)
        bind.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)
                youTubePlayer.loadVideo(getYtbID(youtubeURL), 0f)
                youTubePlayer.pause()
            }
        })
    }

    private fun getYtbID(id: String): String =
        id.replace("https://www.youtube.com/watch?v=", "").substring(0..10)

    private fun initIngredients(ingredients: List<String>?, measurements: List<String>?) {
        if (ingredients == null || measurements == null || ingredients.isEmpty() || measurements.isEmpty()) return

        val adapter = IngredientsAdapter()
        adapter.setList(ingredients, measurements)

        val manager = LinearLayoutManager(applicationContext)
        manager.orientation = LinearLayoutManager.HORIZONTAL

        bind.detIngredient.apply {
            setHasFixedSize(true)
            layoutManager = manager
            this.adapter = adapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
        viewModel.getDetail("").removeObservers(this)
    }
}