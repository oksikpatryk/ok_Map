package com.oksik.okmap.ui.home

import android.os.Bundle
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.oksik.okmap.model.Plant


@BindingAdapter("initMap")
fun initMap(mapView: MapView?, listPlant: List<Plant>?) {
    mapView?.getMapAsync { googleMap ->
        if (listPlant != null) {
            for (plant in listPlant) {
                var latLng = LatLng(plant.location!!.latitude, plant.location!!.longitude)
                googleMap.addMarker(MarkerOptions().position(latLng).title(plant.name))
            }
        }
        googleMap.isMyLocationEnabled = true
    }
}

@BindingAdapter("setTitle")
fun TextView.setTitle(item: Int?) {
    item.let {
        text = when (item) {
            0 -> "loading..."
            else -> item.toString()
        }
    }
}

