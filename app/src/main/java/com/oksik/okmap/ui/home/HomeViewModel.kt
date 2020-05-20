package com.oksik.okmap.ui.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository

class HomeViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)

    private val _allPlants = repository.getAllPlants()

    private var _filteredPlants = MutableLiveData<List<Plant>>()
    val filteredPlants: LiveData<List<Plant>>?
        get() = _filteredPlants

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
        _filteredPlants = repository.getAllPlants()
    }

    fun filterTrees() {
        _filteredPlants.value = _allPlants.value?.filter { plant -> plant.type == "tree" }
    }

    fun filterShrubs() {
        _filteredPlants.value = _allPlants.value?.filter { plant -> plant.type == "shrub" }
    }

    fun filterFlowers() {
        _filteredPlants.value = _allPlants.value?.filter { plant -> plant.type == "flower" }
    }

    fun filterPlants() {
        _filteredPlants.value = _allPlants.value
    }
}