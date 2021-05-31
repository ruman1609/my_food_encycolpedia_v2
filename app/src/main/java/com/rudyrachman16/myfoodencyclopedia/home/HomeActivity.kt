package com.rudyrachman16.myfoodencyclopedia.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.rudyrachman16.myfoodencyclopedia.R
import com.rudyrachman16.myfoodencyclopedia.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    companion object {
        @Volatile
        private var favPress = false
    }

    private var binding: ActivityHomeBinding? = null
    private val bind get() = binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(bind.root)
        setSupportActionBar(bind.toolbar.root)
        bind.bottomNavigationView.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.homeMenu -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.homeFragment)
                    true
                }
                R.id.favoriteMenu -> {
                    startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("myfoodencyclopedia://favorite")
                        )
                    )
                    favPress = true
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (favPress) {
            favPress = false
            bind.bottomNavigationView.selectedItemId = R.id.homeMenu
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}