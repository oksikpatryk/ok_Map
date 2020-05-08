package com.oksik.okmap.repository

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

class FirestoreRepository {

    val TAG = "FIREBASE_REPOSITORY"
    var firestoreDB = FirebaseFirestore.getInstance()
    var user = FirebaseAuth.getInstance().currentUser

    fun getAllPlants(): CollectionReference {
        return firestoreDB.collection("plants")
    }

    fun getPlantsCreatedLast5days(): Query {
        val fiveDaysInMilliseconds = 1000 * 60 * 60 * 24 * 5
        return firestoreDB.collection("plants")
            .whereGreaterThan("createDate", Timestamp(Date(Date().time - fiveDaysInMilliseconds)))
    }

}