package com.oksik.okmap.model

import java.io.Serializable

data class AddressItem(
    var addressId:String? = null,
    var addressName:String? = null,
    var houseName:String? = null,
    var locality:String? = null,
    var landmark:String? = null,
    var latitude:Double? = null,
    var longitude:Double? = null) {

    fun getAddress():String{
        var address = "$houseName, $locality. Landmark: $landmark"
        return address
    }
}