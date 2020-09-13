package com.oksik.okmap.ui.start

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oksik.okmap.ui.home.HomeViewModel

class StartViewModelFactory(private val application: Application): ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartViewModel::class.java))
            return StartViewModel(application) as T
        throw IllegalArgumentException("error")
    }
}