package com.oksik.okmap.model

import com.google.firebase.Timestamp
import java.io.Serializable

data class Plant(
    var id: String? = null,
    var type: String? = null,
    var name: String? = null,
    var latitude: Double? = null,
    var longitude: Double? = null,
    var description: String? = null,
    var miniImageUrl: String? = null,
    var createDate: Timestamp? = null,
    var pictures: List<String>? = null
) : Serializable {
    override fun equals(other: Any?): Boolean {
        val that = other as Plant
        return this.longitude == that.longitude && this.latitude == that.latitude
    }
}