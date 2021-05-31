package com.rudyrachman16.myfoodencyclopedia.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rudyrachman16.core.adapters.GridAdapter
import com.rudyrachman16.core.domain.model.Meal
import com.rudyrachman16.core.utils.GridBoxCount
import com.rudyrachman16.myfoodencyclopedia.databinding.FragmentSearchBinding
import com.rudyrachman16.myfoodencyclopedia.home.HomeFragmentDirections
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.koin.android.ext.android.inject

class SearchFragment : Fragment() {
    private var binding: FragmentSearchBinding? = null
    private val bind get() = binding!!
    private val viewModel: SearchViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return bind.root
    }

    @Suppress("UNCHECKED_CAST")
    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun onResume() {
        super.onResume()

        val adapter = GridAdapter({ bind.searchEmpty.visibility = View.VISIBLE }) {
            val actions =
                HomeFragmentDirections.actionHomeFragmentToDetailActivity(meal = it)
            findNavController().navigate(actions)
            bind.searchBar.setText("")
        }
        bind.searchRV.apply {
            layoutManager = GridLayoutManager(context, GridBoxCount.countBox(requireContext()))
            setHasFixedSize(true)
            this.adapter = adapter
        }

        bind.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().trim().isEmpty()) {
                    adapter.clearList()
                } else {
                    viewModel.setQuery(p0.toString())
                    bind.searchLoad.visibility = View.VISIBLE
                    bind.searchEmpty.visibility = View.GONE
                }
            }

            override fun afterTextChanged(p0: Editable?) = Unit
        })
        viewModel.searchResult.observe(viewLifecycleOwner) {
            bind.searchLoad.visibility = View.GONE
            if (bind.searchBar.text.trim().isNotEmpty()) adapter.setList(it as List<Meal>?)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bind.searchBar.setText("")
        binding = null
    }
}