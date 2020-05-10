package com.oksik.okmap.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oksik.okmap.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val binding = FragmentDashboardBinding.inflate(inflater)
        val dashboardNewPlants = DashboardPlantListAdapter()
        val dashboardAllPlants = DashboardPlantListAdapter()

        binding.lifecycleOwner = this
        binding.viewModel = dashboardViewModel
        binding.dashboardNewPlants.adapter = dashboardNewPlants
        binding.dashboardAllPlants.adapter = dashboardAllPlants

        dashboardViewModel.getPlantsCreatedLast5days.observe(viewLifecycleOwner, Observer { dashboardNewPlants.submitList(it) })
        dashboardViewModel.getAllPlants.observe(viewLifecycleOwner, Observer { dashboardAllPlants.submitList(it) })

        return binding.root
    }
}
