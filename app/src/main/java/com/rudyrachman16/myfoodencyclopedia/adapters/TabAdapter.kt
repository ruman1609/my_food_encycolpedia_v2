package com.rudyrachman16.myfoodencyclopedia.adapters

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rudyrachman16.myfoodencyclopedia.R
import com.rudyrachman16.myfoodencyclopedia.recommended.RecommendedFragment
import com.rudyrachman16.myfoodencyclopedia.search.SearchFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    companion object {
        @StringRes
        val TAB_TITLES = intArrayOf(R.string.recommended, R.string.search)
    }

    override fun getItemCount(): Int = TAB_TITLES.size

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> RecommendedFragment()
        1 -> SearchFragment()
        else -> throw(IllegalArgumentException("Fragment not detected"))
    }
}