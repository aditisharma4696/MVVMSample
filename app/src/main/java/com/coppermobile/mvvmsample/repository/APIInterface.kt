package com.coppermobile.mvvmsample.repository

import com.coppermobile.mvvmsample.models.DishResponse
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("json/menu.json")
    fun getDishes(): Call<List<DishResponse>>
}