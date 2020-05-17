package com.oksik.okmap.ui.dialog_information

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class InformationDialogViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InformationDialogViewModel::class.java))
            return InformationDialogViewModel(application) as T
        throw IllegalArgumentException("InformationDialogViewModelFactory")
    }
}
