package com.oksik.okmap.ui.notifications

import android.app.Application
import androidx.lifecycle.*
import com.oksik.okmap.model.LikedPlant
import com.oksik.okmap.model.Plant
import com.oksik.okmap.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationsViewModel(private val repository: Repository, application: Application) :
    AndroidViewModel(application) {

    private val getLikedPlantsIds = repository.getAllLikedPlantsIds()

    private val _getAllLikedPlants = MutableLiveData<List<Plant>>()
    val getAllLikedPlants: LiveData<List<Plant>> = Transformations.switchMap(getLikedPlantsIds) {likedPlantsIds -> getAllLikedPlants(likedPlantsIds)}

    private fun getAllLikedPlants(list: List<LikedPlant>): MutableLiveData<List<Plant>> {
        var plantsList = listOf<Plant>()
        if (!list.isNullOrEmpty()) {
            for (plant in list) {
                repository.getPlantById(plant.id).get().addOnSuccessListener { result ->
                    plantsList = plantsList.plus(
                        result.toObject(Plant::class.java)
                    ) as List<Plant>
                    _getAllLikedPlants.value = plantsList
                }
                    .addOnFailureListener { }
            }
        }
        return _getAllLikedPlants
    }
}