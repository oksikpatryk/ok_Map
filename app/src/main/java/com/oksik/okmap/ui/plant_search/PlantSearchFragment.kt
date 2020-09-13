package com.oksik.okmap.ui.plant_search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.oksik.okmap.databinding.PlantSearchFragmentBinding


class PlantSearchFragment : Fragment() {
    private lateinit var viewModel: PlantSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = PlantSearchViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlantSearchViewModel::class.java)
        val binding = PlantSearchFragmentBinding.inflate(inflater)
        val plantSearchAdapter = PlantSearchListAdapter(PlantSearchClickListener { plant ->
            this.findNavController().navigate(PlantSearchFragmentDirections.actionPlantSearchFragmentToNavigationHome(plant))
        })

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.plantSearchList.adapter = plantSearchAdapter

        viewModel.allPlants.observe(viewLifecycleOwner, Observer { it?.let { plantSearchAdapter.submitList(it) } })
        viewModel.searchPlantName.observe(viewLifecycleOwner, Observer { it?.let { plantSearchAdapter.filter.filter(it)} })

        return binding.root
    }
}
