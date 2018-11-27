package com.coppermobile.mvvmsample.utils

import com.coppermobile.mvvmsample.dishListRoom.Dish

interface IClickListener {
     fun onItemPressed(dishResponse: Dish)
}