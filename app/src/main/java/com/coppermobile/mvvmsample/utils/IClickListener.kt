package com.coppermobile.mvvmsample.utils

import com.coppermobile.mvvmsample.models.DishResponse

interface IClickListener {
    abstract fun onItemPressed(dishResponse: DishResponse)
}