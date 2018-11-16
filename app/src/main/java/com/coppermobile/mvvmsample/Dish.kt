package com.coppermobile.mvvmsample

import com.google.gson.annotations.SerializedName

class Dish {
    @SerializedName("thumbnail")
    private var thumbnail: String? = null

    @SerializedName("price")
    private var price: Int = 0

    @SerializedName("name")
    private var name: String? = null

    @SerializedName("description")
    private var description: String? = null

    @SerializedName("id")
    private var id: Int = 0

    override fun toString(): String {
        return "Dish(thumbnail=$thumbnail, price=$price, name=$name, description=$description, id=$id)"
    }


}