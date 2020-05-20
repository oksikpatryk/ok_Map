package com.oksik.okmap.ui.notifications

import android.app.Application
import androidx.lifecycle.*
import com.oksik.okmap.model.LikedPlant
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository

class NotificationsViewModel(repository: Repository, application: Application) :
    AndroidViewModel(application) {
    val getAllLikedPlants = repository.allLikedPlants
}