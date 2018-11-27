package com.coppermobile.mvvmsample.dishlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.coppermobile.mvvmsample.dishListRoom.Dish


class DishViewModel(application: Application) : AndroidViewModel(application) {

    fun getDishListObservable(): LiveData<List<Dish>> {
        return DishRepository.getInstance(getApplication()).getDishList()
    }
}