package com.oksik.okmap.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.FirestoreRepository

class HomeViewModel : ViewModel() {

    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = FirestoreRepository()

    private val _getAllPlants = MutableLiveData<List<Plant>>()
    val getAllPlants: LiveData<List<Plant>>?
        get() = _getAllPlants

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