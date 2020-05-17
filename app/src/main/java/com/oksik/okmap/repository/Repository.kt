package com.oksik.okmap.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.oksik.okmap.model.LikedPlant
import com.oksik.okmap.model.Plant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

class Repository(application: Application) {

    private val firestoreDB = FirebaseFirestore.getInstance()
    private var localPlantDatabase: PlantDatabaseDao =
        PlantDatabase.getInstance(application).plantDatabaseDao

     fun getAllLikedPlantsIds() = localPlantDatabase.getAll()

//    val getAllLikedPlantsIds: LiveData<List<LikedPlant>> = localPlantDatabase.getAll()


    suspend fun insert(likedPlant: LikedPlant) {
        withContext(Dispatchers.IO) {
            localPlantDatabase.insertPlant(likedPlant)
        }
    }

    fun getAllPlants(): CollectionReference {
        return firestoreDB.collection("plants")
    }

    fun getPlantById(plantId: String): DocumentReference {
        return firestoreDB.collection("plants").document(plantId)
    }

    fun getPlantsCreatedLast5days(): Query {
        val fiveDaysInMilliseconds = 1000 * 60 * 60 * 24 * 5
        return firestoreDB.collection("plants")
            .whereGreaterThan("createDate", Timestamp(Date(Date().time - fiveDaysInMilliseconds)))
    }

}