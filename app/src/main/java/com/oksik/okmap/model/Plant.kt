package com.oksik.okmap.model

import com.google.firebase.firestore.Exclude
import java.io.Serializable
import java.util.*

data class Plant(
    @get: Exclude
    var id: String? = "",
    var type: String? = "",
    var name: String? = "",
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var createDate: Date? = null,
    var description: String? = "",
    var pictures: MutableList<String>? = mutableListOf()
) : Serializable {
    override fun equals(other: Any?): Boolean {
        val that = other as Plant
        return this.longitude == that.longitude && this.latitude == that.latitude
    }
}