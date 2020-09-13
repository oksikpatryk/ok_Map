package com.oksik.okmap.ui.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.oksik.okmap.databinding.FragmentHomeBinding
import com.oksik.okmap.model.Plant
import kotlin.math.roundToInt


class HomeFragment : Fragment(), OnMapReadyCallback {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1
    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var mMapView: MapView
    val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    private lateinit var homeViewModel: HomeViewModel
    var polyline: Polyline? = null
    var findNearestPlant = false
    private lateinit var lastLocation: Location

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        // 3
        private const val REQUEST_CHECK_SETTINGS = 2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initMapView(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = HomeViewModelFactory(application)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        val binding = FragmentHomeBinding.inflate(inflater)
        mMapView = binding.map1
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.requireActivity())

        val plant = HomeFragmentArgs.fromBundle(requireArguments()).searchedPlant
        findNearestPlant = HomeFragmentArgs.fromBundle(requireArguments()).findNearestPlant

        if (plant != null) {
            homeViewModel.setSearchedPlant(plant)
            homeViewModel.setSelectedPlant(plant)
        }

        binding.lifecycleOwner = this
        binding.viewModel = homeViewModel

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation


                if (homeViewModel.selectedPlant.value == null) {
                    removeDrawPolyline(null, null)
                }
                else if (homeViewModel.selectedPlant.value != null) {
                    val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                    val plantLatLang = LatLng(
                        homeViewModel.selectedPlant.value!!.latitude!!,
                        homeViewModel.selectedPlant.value!!.longitude!!
                    )
                    val plantLoc = Location("plantLoc")
                    plantLoc.latitude = plantLatLang.latitude
                    plantLoc.longitude = plantLatLang.longitude
                    removeDrawPolyline(latLng, plantLatLang)

                    homeViewModel.distanceToPlant.value =
                        lastLocation.distanceTo(plantLoc).roundToInt().toString() + " m"
                }
//                placeMarkerOnMap(LatLng(lastLocationcation.latitude, lastLocation.longitude))
            }
        }

        setOnClickListeners(binding)
        createLocationRequest()
        return binding.root
    }

    private fun setOnClickListeners(binding: FragmentHomeBinding) {
        binding.cardViewInformation.setOnClickListener {
            this.findNavController()
                .navigate(
                    HomeFragmentDirections.actionNavigationHomeToInformationDialogFragment(
                        homeViewModel.selectedPlant.value
                    )
                )
        }

        binding.nearestPlantsButton.setOnClickListener {
            enableMyLocation(true)
        }
    }

    private fun initMapView(savedInstanceState: Bundle?) {
        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY)
        }
        mMapView.onCreate(mapViewBundle)
        mMapView.onResume()
        mMapView.getMapAsync(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            enableMyLocation(findNearestPlant);
        } else {
            permissionDenied = true;
        }

    }

    private fun isPermissionGranted(
        grantPermissions: Array<String>, grantResults: IntArray,
        permission: String
    ): Boolean {
        for (i in grantPermissions.indices) {
            if (permission == grantPermissions[i]) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED
            }
        }
        return false
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

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.setOnMarkerClickListener { marker ->
            homeViewModel.setSelectedPlant(marker.tag as Plant)
            enableMyLocation(false)
            false
        }

        googleMap.setOnMapClickListener {
            homeViewModel.setSelectedPlant(null)
            removeDrawPolyline(null, null)
        }

        googleMap.setPadding(0,70,0,0)
        enableMyLocation(findNearestPlant)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
        enableMyLocation(findNearestPlant)
    }

    private fun enableMyLocation(findNearestPlant: Boolean = false) {
        if (this::map.isInitialized) {
            if (ContextCompat.checkSelfPermission(
                    this.requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 10)
            } else {
                map.isMyLocationEnabled = true

                fusedLocationClient.lastLocation
                    .addOnSuccessListener(requireActivity()) { location : Location? ->

                        val allPlants = homeViewModel.allPlants.value
                        if (location != null && findNearestPlant && allPlants != null) {
                            var minDistance = Float.MAX_VALUE
                            var nearestPlant = Plant()
                            for (plant in allPlants) {
                                val plantLoc = Location("plantLoc")
                                plantLoc.latitude = plant.latitude!!
                                plantLoc.longitude = plant.longitude!!
                                val distance = location.distanceTo(plantLoc)
                                if (distance < minDistance) {
                                    minDistance = distance
                                    nearestPlant = plant
                                }
                            }
                            homeViewModel.setSelectedPlant(nearestPlant)
                            val plantLatLng = LatLng(
                                nearestPlant.latitude!!,
                                nearestPlant.longitude!!
                            )
                            val locLatLatLng = LatLng(location.latitude, location.longitude)
                            val plantLoc = Location("plantLoc")
                            plantLoc.latitude = nearestPlant.latitude!!
                            plantLoc.longitude = nearestPlant.longitude!!
                            removeDrawPolyline(locLatLatLng, plantLatLng)
                            homeViewModel.distanceToPlant.value = location.distanceTo(plantLoc).roundToInt().toString() + " m"
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(plantLatLng, 15F))
                        }
                        else {
                            if (location != null && homeViewModel.selectedPlant.value == null) {
                                val latLng = LatLng(location.latitude, location.longitude)
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
                            }
                            else if (location != null && homeViewModel.selectedPlant.value != null) {
                                val latLng = LatLng(location.latitude, location.longitude)
                                val plantLatLang = LatLng(
                                    homeViewModel.selectedPlant.value!!.latitude!!,
                                    homeViewModel.selectedPlant.value!!.longitude!!
                                )
                                val plantLoc = Location("plantLoc")
                                plantLoc.latitude = plantLatLang.latitude
                                plantLoc.longitude = plantLatLang.longitude
                                removeDrawPolyline(latLng, plantLatLang)

                                homeViewModel.distanceToPlant.value = location.distanceTo(plantLoc).roundToInt().toString() + " m"
                            }
                        }
                    }
            }
        }
    }

    private fun removeDrawPolyline(
        from: LatLng?,
        to: LatLng?
    ) {
        polyline?.remove()
        if (from == null || to == null) return
        polyline = map.addPolyline(
            PolylineOptions()
                .add(from, to)
                .width(5f)
                .color(Color.RED)
        )
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE)
            return
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        val client = LocationServices.getSettingsClient(requireActivity())
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CHECK_SETTINGS) {
            if (resultCode == Activity.RESULT_OK) {
                locationUpdateState = true
                startLocationUpdates()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}

