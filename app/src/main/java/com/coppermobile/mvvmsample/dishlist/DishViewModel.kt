package com.coppermobile.mvvmsample.dishlist

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.coppermobile.mvvmsample.models.DishResponse


class DishViewModel : AndroidViewModel {
    private var projectListObservable: LiveData<List<DishResponse>>

    constructor(application: Application) : super(application) {
        projectListObservable = DishRepository.instance.getDishList()
    }

    fun getDishListObservable(): LiveData<List<DishResponse>> {
        return projectListObservable
    }
}