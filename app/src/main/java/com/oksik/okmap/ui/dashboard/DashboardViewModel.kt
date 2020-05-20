package com.oksik.okmap.ui.dashboard

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository

class DashboardViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)

    private var _getAllPlants = MutableLiveData<List<Plant>>()
    val getAllPlants: LiveData<List<Plant>>
        get() = _getAllPlants

    private var _getPlantsCreatedLast5days = MutableLiveData<List<Plant>>()
    val getPlantsCreatedLast5days: LiveData<List<Plant>>
        get() = _getPlantsCreatedLast5days

    init {
        _getAllPlants = repository.getAllPlants()
        _getPlantsCreatedLast5days = repository.getPlantsCreatedLast5days()
    }
}