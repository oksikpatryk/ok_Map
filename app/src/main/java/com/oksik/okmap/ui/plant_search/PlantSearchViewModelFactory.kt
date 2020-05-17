package com.oksik.okmap.ui.plant_search

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PlantSearchViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlantSearchViewModel::class.java))
            return PlantSearchViewModel(application) as T
        throw IllegalArgumentException("dupa3")
    }
}