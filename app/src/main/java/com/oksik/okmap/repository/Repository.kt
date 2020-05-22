package com.oksik.okmap.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.oksik.okmap.model.LikedPlant
import com.oksik.okmap.model.Plant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class Repository(application: Application) {

    private val TAG = "repository"
    private val firestoreDB = FirebaseFirestore.getInstance()
    private var localPlantDatabase: PlantDatabaseDao =
        PlantDatabase.getInstance(application).plantDatabaseDao

    private val likedPlantsIds = localPlantDatabase.getAll()
    private val _allLikedPlants = MutableLiveData<List<Plant>>()
    val allLikedPlants: LiveData<List<Plant>> =
        Transformations.switchMap(likedPlantsIds) { getAllLikedPlants() }

    fun getAllPlants(): MutableLiveData<List<Plant>> {
        val allPlants = MutableLiveData<List<Plant>>()
        firestoreDB.collection("plants").get()
            .addOnSuccessListener { result ->
                allPlants.value = getPlantsWithIds(result)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return allPlants
    }

    private fun getAllLikedPlants(): MutableLiveData<List<Plant>> {
        var plantsList = listOf<Plant>()
        if (!likedPlantsIds.value.isNullOrEmpty()) {
            for (plant in likedPlantsIds.value!!) {
                firestoreDB.collection("plants").document(plant.id).get()
                    .addOnSuccessListener { result ->
                        plantsList = plantsList.plus(
                            result.toObject(Plant::class.java)
                        ) as List<Plant>
                        _allLikedPlants.value = plantsList
                    }
                    .addOnFailureListener { exception ->
                        Log.d(TAG, "Error getting documents: ", exception)
                    }
            }
        }
        return _allLikedPlants
    }

    fun getPlantsCreatedLast5days(): MutableLiveData<List<Plant>> {
        val plantsCreatedLast5days = MutableLiveData<List<Plant>>()
        val fiveDaysInMilliseconds = 1000 * 60 * 60 * 24 * 5
        firestoreDB.collection("plants")
            .whereGreaterThan("createDate", Timestamp(Date(Date().time - fiveDaysInMilliseconds)))
            .get()
            .addOnSuccessListener { result ->
                plantsCreatedLast5days.value = getPlantsWithIds(result)
            }.addOnFailureListener { exception ->
                Log.d(TAG, "Error getting documents: ", exception)
            }
        return plantsCreatedLast5days
    }

    suspend fun likePlant(likedPlant: LikedPlant) {
        withContext(Dispatchers.IO) {
            localPlantDatabase.insertPlant(likedPlant)
        }
    }

    suspend fun delete() {
        withContext(Dispatchers.IO) {
            localPlantDatabase.deleteAllPlants()
        }
    }

    private fun getPlantsWithIds(result: QuerySnapshot): MutableList<Plant> {
        val list = result.toObjects(Plant::class.java)
        for (p in result) {
            val plant = p.toObject(Plant::class.java)
            plant.id = p.id
            for (l in list)
                if (plant == l) {
                    l.id = plant.id
                    break
                }
        }
        return list
    }
}