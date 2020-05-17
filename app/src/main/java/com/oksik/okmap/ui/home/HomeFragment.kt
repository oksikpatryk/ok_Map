package com.oksik.okmap.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.oksik.okmap.databinding.FragmentHomeBinding
import com.oksik.okmap.model.Plant


class HomeFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMapView: MapView
    val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(application)
        homeViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        val binding = FragmentHomeBinding.inflate(inflater)
        val plant = HomeFragmentArgs.fromBundle(requireArguments()).searchedPlant

        if (plant != null) {
            homeViewModel.setSearchedPlant(plant)
            homeViewModel.setSelectedPlant(plant)
        }

        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel

        mMapView = binding.map1

        if (ContextCompat.checkSelfPermission(
                this.requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 10)
        } else {
            initMapView(savedInstanceState)
        }

        setOnClickListeners(binding)
        return binding.root
    }

    private fun setOnClickListeners(binding: FragmentHomeBinding) {
        binding.cardViewInformation.setOnClickListener {
            this.findNavController()
                .navigate(
                    HomeFragmentDirections.actionNavigationHomeToInformationDialogFragment(
                        homeViewModel.selectedPlant?.value
                    )
                )
        }

        binding.plantSearchBtn.setOnClickListener {
            this.findNavController()
                .navigate(HomeFragmentDirections.actionNavigationHomeToPlantSearchFragment())
        }
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView.onCreate(mapViewBundle)
        mMapView.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle)
        }
        mMapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        mMapView.onResume()
        super.onResume()
    }

    override fun onStart() {
        mMapView.onStart()
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
        mMapView.onStop()
    }

    override fun onPause() {
        mMapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mMapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        mMapView.onLowMemory()
        super.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMap.setOnMarkerClickListener { marker ->
            homeViewModel.setSelectedPlant(marker.tag as Plant)
            false
        }

    }
}

