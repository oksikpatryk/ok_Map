package com.oksik.okmap.ui.start

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.oksik.okmap.databinding.StartFragmentBinding

class StartFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    companion object {
        fun newInstance() = StartFragment()
    }

    private lateinit var viewModel: StartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = StartViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(StartViewModel::class.java)
        var binding = StartFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.nearestPlantsButton.setOnClickListener {
            this.findNavController().navigate(StartFragmentDirections.actionStartFragmentToNavigationHome(null, true))
        }

        binding.goToSearchPlantButton.setOnClickListener {
            this.findNavController().navigate(StartFragmentDirections.actionStartFragmentToPlantSearchFragment())
        }

//        getNearestPlant()
        return binding.root
    }

//    private fun getNearestPlant() {
//        fusedLocationClient =
//            LocationServices.getFusedLocationProviderClient(this.requireActivity())
//        if (ContextCompat.checkSelfPermission(
//                this.requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 10)
//        } else {
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    if (location != null) {
//                        val latLng = LatLng(location.latitude, location.longitude)
//                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
//
//                        var minDistance = 0
//                        var closestLatLng: LatLng? = null
//                        for (plant in viewModel.allPlants.value!!) {
//                            val latit: Double = plant.latitude!!
//                            val longit: Double = plant.longitude!!
//                            val newLatLng = LatLng(latit, longit)
//                            val distance: Double = distanceHaversine(userlatit, latit, userlongit, longit)
//                            if (distance < minDistance) {
//                                closestLatLng = newLatLng
//                                minDistance = distance.toInt()
//                            }
//                        }
//                        if (closestLatLng != null && minDistance < 600) {
//                            drawMarker(closestLatLng)
//                        }
//                    }
//                }
//        }
//    }
}