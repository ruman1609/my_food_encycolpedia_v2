package com.rudyrachman16.myfoodencyclopedia.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.rudyrachman16.core.utils.CategorySetter
import com.rudyrachman16.myfoodencyclopedia.adapters.TabAdapter
import com.rudyrachman16.myfoodencyclopedia.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val bind get() = binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bind.homeVP.adapter = TabAdapter(requireActivity() as AppCompatActivity)
        TabLayoutMediator(bind.homeTab, bind.homeVP) { tab, pos ->
            tab.text =
                if (pos == 0) getString(TabAdapter.TAB_TITLES[pos], CategorySetter.getCategory())
                else getText(TabAdapter.TAB_TITLES[pos])
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}