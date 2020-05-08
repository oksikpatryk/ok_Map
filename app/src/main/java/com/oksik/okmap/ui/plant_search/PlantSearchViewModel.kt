package com.oksik.okmap.ui.plant_search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.FirestoreRepository

class PlantSearchViewModel : ViewModel() {
    private var firestoreRepository = FirestoreRepository()

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
