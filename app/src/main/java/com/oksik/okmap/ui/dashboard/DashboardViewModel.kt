package com.oksik.okmap.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.QuerySnapshot
import com.oksik.okmap.model.AddressItem
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.FirestoreRepository

class DashboardViewModel : ViewModel() {

    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = FirestoreRepository()

    private val _getAllPlants = MutableLiveData<MutableList<Plant>>()
    val getAllPlants: LiveData<MutableList<Plant>>?
        get() = _getAllPlants

    init {
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