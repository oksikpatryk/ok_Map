package com.oksik.okmap.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.oksik.okmap.databinding.FragmentNotificationssBinding
import com.oksik.okmap.ui.dashboard.DashboardPlantListAdapter

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val notificationViewModelFactory = NotificationViewModelFactory(application)
        notificationsViewModel =
            ViewModelProvider(this, notificationViewModelFactory).get(NotificationsViewModel::class.java)
        val binding = FragmentNotificationssBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = notificationsViewModel

        val dashboardNewPlants = DashboardPlantListAdapter()
        binding.likedPlantsList.adapter = dashboardNewPlants

//        notificationsViewModel.getAllLikedPlants.observe(viewLifecycleOwner, Observer { dashboardNewPlants.submitList(it)})

        return binding.root
    }
}
