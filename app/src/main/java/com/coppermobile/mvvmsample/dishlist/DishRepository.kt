package com.coppermobile.mvvmsample.dishlist

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.coppermobile.mvvmsample.models.DishResponse
import com.coppermobile.mvvmsample.repository.APIInterface
import com.coppermobile.mvvmsample.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DishRepository {

    private var dishInterface: APIInterface

    private constructor() {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        dishInterface = retrofit.create<APIInterface>(APIInterface::class.java)
    }

    private object GetInstance {
        val INSTANCE = DishRepository()
    }

    fun getDishList(): LiveData<List<DishResponse>> {
        val data = MutableLiveData<List<DishResponse>>()
        dishInterface?.getDishes()?.enqueue(object : Callback<List<DishResponse>> {
            override fun onFailure(call: Call<List<DishResponse>>?, t: Throwable?) {
                data.value = null
            }

            override fun onResponse(call: Call<List<DishResponse>>?, response: Response<List<DishResponse>>?) {
                data.value = response?.body()
            }

        })

        return data

    }

    companion object {
        val instance: DishRepository by lazy { GetInstance.INSTANCE }
    }


}