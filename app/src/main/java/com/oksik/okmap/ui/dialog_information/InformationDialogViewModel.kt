package com.oksik.okmap.ui.dialog_information

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    
    val selectedPlant = MutableLiveData<Plant>()

    private val _getSelectedPlantPictures = MutableLiveData<List<String>>()
    val getSelectedPlantPictures: LiveData<List<String>>
        get() {
            _getSelectedPlantPictures.value = selectedPlant.value?.pictures
            return _getSelectedPlantPictures
        }

    private val _getImagePositionAllImages = MutableLiveData<String>()
    val getImagePositionAllImages: LiveData<String>
        get() = _getImagePositionAllImages

    fun setImagePosition(position: Int){
        _getImagePositionAllImages.value = "${position + 1}/${getSelectedPlantPictures.value?.size}"
    }

    fun insertLikedPlant() {
        uiScope.launch {
            val likedPlant = LikedPlant(selectedPlant.value!!.id!!)
            repository.likePlant(likedPlant)
//            repository.delete()
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
