package com.coppermobile.mvvmsample.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class DishResponse() {
    @SerializedName("thumbnail")
    var thumbnail: String? = null

    @SerializedName("price")
    var price: Int = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("id")
    var id: Int = 0

    override fun toString(): String {
        return "DishResponse(thumbnail=$thumbnail, price=$price, name=$name, description=$description, id=$id)"
    }

}