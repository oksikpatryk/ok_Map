package com.oksik.okmap.model

import androidx.lifecycle.LiveData
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
        return this.id == that.id
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (type?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (latitude?.hashCode() ?: 0)
        result = 31 * result + (pictures?.hashCode() ?: 0)
        result = 31 * result + (longitude?.hashCode() ?: 0)
        result = 31 * result + (createDate?.hashCode() ?: 0)
        result = 31 * result + (description?.hashCode() ?: 0)
        result = 31 * result + (miniImageUrl?.hashCode() ?: 0)
        return result
    }
}