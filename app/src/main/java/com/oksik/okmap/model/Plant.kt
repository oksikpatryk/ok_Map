package com.oksik.okmap.model

import com.google.firebase.firestore.GeoPoint
import java.io.Serializable

data class Plant(
    var name: String? = null,
    var location: GeoPoint? = null,
    var description: String? = null,
    var miniImageUrl: String? = null
) : Serializable