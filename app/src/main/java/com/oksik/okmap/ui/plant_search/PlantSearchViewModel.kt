package com.oksik.okmap.ui.plant_search

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository

class PlantSearchViewModel(application: Application) : ViewModel() {
    private var firestoreRepository = Repository(application)

    private val _allPlants = MutableLiveData<List<Plant>>()
    val allPlants: LiveData<List<Plant>>
        get() = _allPlants

    val searchPlantName = MutableLiveData<String>()

    init {
        getAllPlants()
    }

    private fun getAllPlants() {
        firestoreRepository.getAllPlants().get()
            .addOnFailureListener { exception ->
                Log.d("TAG", "Error getting documents: ", exception)
            }
            .addOnSuccessListener { result ->
                _allPlants.value = result.toObjects(Plant::class.java)
                Log.d("TAG", _allPlants.value.toString())
            }
    }
}
