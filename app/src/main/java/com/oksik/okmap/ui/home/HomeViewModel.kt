package com.oksik.okmap.ui.home

import android.util.Log
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.GeoPoint
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.FirestoreRepository

class HomeViewModel : ViewModel() {

    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = FirestoreRepository()

    private val _getAllPlants = MutableLiveData<List<Plant>>()
    val getAllPlants: LiveData<List<Plant>>?
        get() = _getAllPlants

    private val _selectedPlant = MutableLiveData<Plant>()
    val selectedPlant: LiveData<Plant>?
        get() = _selectedPlant

    fun setSelectedPlant(plant: Plant){
        _selectedPlant.value = plant
    }

//    val selectedPlant: LiveData<Plant>
//        get() = _selectedPlant

    init {
        _getAllPlants.value = emptyList()
        getSavedAddresses()
    }

    private fun getSavedAddresses() {
        firebaseRepository.getSavedAddress().get()
            .addOnSuccessListener { result ->
                _getAllPlants.value = result.toObjects(Plant::class.java)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}