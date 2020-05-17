package com.oksik.okmap.ui.notifications

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.oksik.okmap.repository.Repository

class NotificationViewModelFactory(private val application: Application) :
    ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotificationsViewModel::class.java))
            return NotificationsViewModel(Repository(application), application) as T
        throw IllegalArgumentException("NotificationViewModelFactory error")
    }
}