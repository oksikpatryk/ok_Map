package com.oksik.okmap.ui

import com.google.firebase.firestore.QuerySnapshot
import com.oksik.okmap.model.Plant

fun getPlantsWithIds(result: QuerySnapshot): MutableList<Plant> {
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