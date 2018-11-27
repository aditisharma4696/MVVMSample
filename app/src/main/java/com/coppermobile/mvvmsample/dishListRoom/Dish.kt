package com.coppermobile.mvvmsample.dishListRoom

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.io.Serializable

@Entity(tableName = "dish_table")
data class Dish(

        @NonNull
        @PrimaryKey
        @ColumnInfo(name = "dish_id")
        var dishId: Int,
        @ColumnInfo(name = "dish_image")
        var dishImage: String?,
        @ColumnInfo(name = "dish_name")
        var dishName: String?,
        @ColumnInfo(name = "dish_description")
        var dishDescription: String?,
        @ColumnInfo(name = "dish_price")
        var dishPrice: String?

)
