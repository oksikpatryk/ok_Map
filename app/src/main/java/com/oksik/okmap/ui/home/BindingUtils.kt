package com.oksik.okmap.ui.home

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.oksik.okmap.R
import com.oksik.okmap.model.Plant


@BindingAdapter("initMap")
fun initMap(mapView: MapView?, listPlant: List<Plant>?) {
    mapView?.getMapAsync { googleMap ->
        if (listPlant != null) {
            for (plant in listPlant) {
                val icon: BitmapDescriptor = when (plant.type) {
                    "tree" -> BitmapDescriptorFactory.fromResource(R.drawable.marker_tree)
                    "flower" -> BitmapDescriptorFactory.fromResource(R.drawable.marker_flower)
                    else -> BitmapDescriptorFactory.fromResource(R.drawable.marker)
                }

                val latLng = LatLng(plant.latitude!!, plant.longitude!!)
                val newMarker =
                    googleMap.addMarker(MarkerOptions().position(latLng).title(plant.name))
                newMarker.tag = plant
                newMarker.setIcon(icon)
            }
        }
        googleMap.setMaxZoomPreference( 17.0f )
    }
}

@BindingAdapter("myLocation")
fun myLocation(mapView: MapView?, permissionGranted: Boolean) {
//    mapView?.getMapAsync { googleMap ->
//        if (ContextCompat.checkSelfPermission(
//                this.requireContext(),
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 10)
//        } else {
//            googleMap.isMyLocationEnabled = true
//        }
//    }
}

@BindingAdapter("enabledMyLocation")
fun enabledMyLocation(mapView: MapView?, enableMyLocation: Boolean) {
    if (enableMyLocation) {
        Log.i("s", "s")
        mapView?.getMapAsync { googleMap ->
            googleMap.isMyLocationEnabled = true
        }
    }
}

@BindingAdapter("zoomCenterToPlant")
fun zoomCenterToPlant(mapView: MapView?, plant: Plant?) {
    mapView?.getMapAsync { googleMap ->
        if (plant != null) {
            val latLng = LatLng(plant.latitude!!, plant.longitude!!)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
        }
//        else {
//            val latLng = LatLng(googleMap.myLocation.latitude, googleMap.myLocation.longitude)
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15F))
//        }
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

@BindingAdapter("setMiniImage")
fun setMiniImage(imgView: ImageView, imgUrl: String?) {
    imgView.let {
        val options = RequestOptions()
        options.centerCrop()
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(options)
            .into(imgView)
    }
}

@BindingAdapter("setPlantName")
fun TextView.setPlantName(name: String) {
    name.let { text = name }
}

//@BindingAdapter("nic")
//fun nic(mapView: MapView?, selectedPlant: Plant?) {
//    mapView?.getMapAsync { googleMap ->
//        googleMap.setOnMarkerClickListener { marker ->
//            Log.i("duuu", marker.title)
//            false
//        }
//    }
//}

//
//@BindingAdapter("numberOfSets_alternative")
//fun setNumberOfSets_alternative(mapView: MapView, value: Plant?) {
//    Log.i("duuu", "setNumberOfSets_alternative")
//
////    view.setText(String.format(
////        view.resources.getString(R.string.sets_format,
////            value[0] + 1,
////            value[1])))
//}
//
//@InverseBindingAdapter(attribute = "numberOfSets_alternative")
//fun getNumberOfSets_alternative(mapView: MapView): Plant {
//    Log.i("duuu", "getNumberOfSets_alternative")
//    return Plant(GeoPoint(5.0, 9.0), "ppp", "ooo")
//}
//
//@BindingAdapter("numberOfSets_alternativeAttrChanged")
//fun setListener(mapView: MapView, listener: InverseBindingListener?) {
//    Log.i("duuu", "setListener")
//
//    mapView.getMapAsync { googleMap ->
//        googleMap.setOnMarkerClickListener { marker ->
//            Log.i("duuu", marker.title)
//            listener?.onChange()
//            false
//        }
//    }
//}
