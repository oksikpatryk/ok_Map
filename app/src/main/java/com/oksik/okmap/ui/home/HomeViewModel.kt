package com.oksik.okmap.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository
import com.oksik.okmap.ui.getPlantsWithIds

class HomeViewModel(application: Application) : ViewModel() {

    val TAG = "FIRESTORE_VIEW_MODEL"
    var firebaseRepository = Repository(application)

    private val _getAllPlants = MutableLiveData<List<Plant>>()

    private val _getAllPlantsToShow = MutableLiveData<List<Plant>>()
    val getAllPlantsToShow: LiveData<List<Plant>>?
        get() = _getAllPlantsToShow

    private val _selectedPlant = MutableLiveData<Plant>()
    val selectedPlant: LiveData<Plant>?
        get() = _selectedPlant

    fun setSelectedPlant(plant: Plant) {
        _selectedPlant.value = plant
    }

    private val _searchedPlant = MutableLiveData<Plant>()
    val searchedPlant: LiveData<Plant>?
        get() = _searchedPlant

    fun setSearchedPlant(plant: Plant) {
        _searchedPlant.value = plant
    }

    init {
        _getAllPlants.value = emptyList()
        getAllPlantsFromDb()
    }

    private fun getAllPlantsFromDb() {
        firebaseRepository.getAllPlants().get()
            .addOnSuccessListener { result ->
                _getAllPlants.value = getPlantsWithIds(result)
                _getAllPlantsToShow.value = _getAllPlants.value
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
    }

    fun getAllTrees() {
        _getAllPlantsToShow.value = _getAllPlants.value?.filter { plant -> plant.type == "tree" }
    }

    fun getAllShrubs() {
        _getAllPlantsToShow.value = _getAllPlants.value?.filter { plant -> plant.type == "shrub" }
    }

    fun getAllFlowers() {
        _getAllPlantsToShow.value = _getAllPlants.value?.filter { plant -> plant.type == "flower" }
    }

    fun getAllPlants() {
        _getAllPlantsToShow.value = _getAllPlants.value
    }
}