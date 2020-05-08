package com.oksik.okmap.ui.plant_search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.oksik.okmap.R
import com.oksik.okmap.databinding.PlantSearchFragmentBinding


class PlantSearchFragment : Fragment() {
    private lateinit var viewModel: PlantSearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(PlantSearchViewModel::class.java)
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
