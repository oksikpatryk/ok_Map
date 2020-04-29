package com.oksik.okmap.ui.dialog_information

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oksik.okmap.model.Plant

class InformationDialogViewModel : ViewModel() {
    val selectedPlant = MutableLiveData<Plant>()
//    val selectedPlant: LiveData<Plant>
//        get() = _selectedPlant
}
