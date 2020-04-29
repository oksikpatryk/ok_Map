package com.oksik.okmap.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
        var binding = FragmentDashboardBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = dashboardViewModel
//        binding.textDashboard.text = dashboardViewModel.getAllPlants?.value?.size.toString()
        return binding.root
    }
}
