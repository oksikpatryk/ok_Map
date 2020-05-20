package com.oksik.okmap.ui.plant_search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository

class PlantSearchViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)

    val searchPlantName = MutableLiveData<String>()

    val allPlants: LiveData<List<Plant>> = repository.getAllPlants()
}
