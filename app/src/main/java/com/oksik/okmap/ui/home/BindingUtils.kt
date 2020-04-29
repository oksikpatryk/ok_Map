package com.oksik.okmap.ui.home

import android.os.RemoteException
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.google.android.gms.internal.maps.zzb
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.internal.IGoogleMapDelegate
import com.google.android.gms.maps.internal.zzar
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.RuntimeRemoteException
import com.google.firebase.firestore.GeoPoint
import com.oksik.okmap.model.Plant


@BindingAdapter("initMap")
fun initMap(mapView: MapView?, listPlant: List<Plant>?) {
    mapView?.getMapAsync { googleMap ->
        if (listPlant != null) {
            for (plant in listPlant) {
                var latLng = LatLng(plant.location!!.latitude, plant.location!!.longitude)
                var newMarker =
                    googleMap.addMarker(MarkerOptions().position(latLng).title(plant.name))
                newMarker.tag = plant
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

@BindingAdapter("setMiniImage")
fun setMiniImage(imgView: ImageView, imgUrl: String?) {
    imgView.let {
        Glide.with(imgView.context).load(imgUrl).into(imgView)
    }
}

@BindingAdapter("setPlantName")
fun TextView.setPlantName(name: Plant) {
    name.let { text = name.name }
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
