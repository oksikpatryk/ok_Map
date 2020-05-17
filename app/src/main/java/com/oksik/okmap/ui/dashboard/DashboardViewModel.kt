package com.oksik.okmap.ui.dashboard

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository
import com.oksik.okmap.ui.getPlantsWithIds

class DashboardViewModel(application: Application) : ViewModel() {

    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = Repository(application)

    private val _getAllPlants = MutableLiveData<List<Plant>>()
    val getAllPlants: LiveData<List<Plant>>
        get() = _getAllPlants


    private val _getPlantsCreatedLast5days = MutableLiveData<List<Plant>>()
    val getPlantsCreatedLast5days: LiveData<List<Plant>>
        get() = _getPlantsCreatedLast5days

    val all = _getPlantsCreatedLast5days

    init {
        getAllPlants()
        getPlantsCreatedLast5days()
    }

    private fun getAllPlants() {
        firebaseRepository.getAllPlants().get()
            .addOnSuccessListener { result ->
                _getAllPlants.value = getPlantsWithIds(result)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    private fun getPlantsCreatedLast5days() {
        firebaseRepository.getPlantsCreatedLast5days().get()
            .addOnSuccessListener { result ->
                _getPlantsCreatedLast5days.value = getPlantsWithIds(result)
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }
}