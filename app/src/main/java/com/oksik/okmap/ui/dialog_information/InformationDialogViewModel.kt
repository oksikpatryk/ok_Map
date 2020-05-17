package com.oksik.okmap.ui.dialog_information

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oksik.okmap.model.LikedPlant
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class InformationDialogViewModel(application: Application) : ViewModel() {
    private val repository = Repository(application)
    private val viewModelJob = Job()
    val selectedPlant = MutableLiveData<Plant>()
    val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    val selectedPlant: LiveData<Plant>
//        get() = _selectedPlant

    fun insert(){
        uiScope.launch {
            val likedPlant = LikedPlant(selectedPlant.value!!.id!!)
            repository.insert(likedPlant)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
