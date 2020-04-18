package com.oksik.okmap.model

import com.google.firebase.firestore.GeoPoint

data class Plant(
    var location: GeoPoint? = null,
    var name: String? = null,
    var description: String? = null
)