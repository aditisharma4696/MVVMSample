package com.coppermobile.mvvmsample.dishListRoom

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface DishDAO {

    @Insert
    fun insertData(data: List<Dish>)

    @Delete
    fun deleteData(data: Dish)

    @Query("select * from dish_table")
    fun getAllDishes(): LiveData<List<Dish>>
}