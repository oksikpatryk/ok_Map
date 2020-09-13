package com.oksik.okmap.ui.start

import android.app.Application
import androidx.lifecycle.ViewModel
import com.oksik.okmap.repository.Repository

class StartViewModel(application: Application) : ViewModel() {

    private val repository = Repository(application)

    val allPlants = repository.getAllPlants()
}