package com.oksik.okmap.ui.plant_search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.oksik.okmap.R
import com.oksik.okmap.databinding.PlantSearchFragmentBinding


class PlantSearchFragment : Fragment() {

    companion object {
        fun newInstance() = PlantSearchFragment()
    }

    private lateinit var viewModel: PlantSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(PlantSearchViewModel::class.java)
        val binding = PlantSearchFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val plantSearchAdapter = PlantSearchListAdapter()

        binding.plantSearchList.adapter = plantSearchAdapter
        viewModel.allPlants.observe(viewLifecycleOwner, Observer {
            it?.let { plantSearchAdapter.submitList(it) }
        })

        return binding.root
    }
}
