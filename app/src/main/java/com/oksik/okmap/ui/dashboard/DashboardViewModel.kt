package com.oksik.okmap.ui.dashboard

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.FirestoreRepository

class DashboardViewModel : ViewModel() {

    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = FirestoreRepository()

    private val _getAllPlants = MutableLiveData<List<Plant>>()
    val getAllPlants: LiveData<List<Plant>>
        get() = _getAllPlants


    private val _getPlantsCreatedLast5days = MutableLiveData<List<Plant>>()
    val getPlantsCreatedLast5days: LiveData<List<Plant>>
        get() = _getPlantsCreatedLast5days

    init {
        getAllPlants()
        getPlantsCreatedLast5days()
    }

    private fun getAllPlants() {
        firebaseRepository.getAllPlants().get()
            .addOnSuccessListener { result ->
                _getAllPlants.value = result.toObjects(Plant::class.java)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun getPlantsCreatedLast5days() {
        firebaseRepository.getPlantsCreatedLast5days().get()
            .addOnSuccessListener { result ->
                _getPlantsCreatedLast5days.value = result.toObjects(Plant::class.java)
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}