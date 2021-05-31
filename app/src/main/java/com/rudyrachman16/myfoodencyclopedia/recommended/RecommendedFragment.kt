package com.rudyrachman16.myfoodencyclopedia.recommended

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.rudyrachman16.core.adapters.GridAdapter
import com.rudyrachman16.core.data.Status
import com.rudyrachman16.core.utils.GridBoxCount
import com.rudyrachman16.myfoodencyclopedia.R
import com.rudyrachman16.myfoodencyclopedia.databinding.FragmentRecommendedBinding
import com.rudyrachman16.myfoodencyclopedia.home.HomeFragmentDirections
import org.koin.android.ext.android.inject

class RecommendedFragment : Fragment() {
    private var binding: FragmentRecommendedBinding? = null
    private val bind get() = binding!!

    private val viewModel: RecommendedViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecommendedBinding.inflate(inflater, container, false)
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = GridAdapter({ bind.recomEmpty.visibility = View.VISIBLE }) {
            val actions =
                HomeFragmentDirections.actionHomeFragmentToDetailActivity(it.idMeal, it.strMeal)
            findNavController().navigate(actions)
        }
        bind.recRV.apply {
            layoutManager = GridLayoutManager(context, GridBoxCount.countBox(requireContext()))
            setHasFixedSize(true)
            this.adapter = adapter
        }

        viewModel.recommended.observe(viewLifecycleOwner) {
            when (it) {
                is Status.Success<*> -> {
                    goingGone()
                    adapter.setList(it.data)
                }
                is Status.Loading<*> -> goingGone(View.VISIBLE)
                is Status.Error<*> -> {
                    goingGone()
                    Toast.makeText(
                        context, getString(R.string.error_status, it.error), Toast.LENGTH_LONG
                    ).show()
                    adapter.setList(it.data)
                }
            }
        }
    }

    private fun goingGone(status: Int = View.GONE) {
        bind.recomEmpty.visibility = View.GONE
        bind.loadingRecom.visibility = status
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}