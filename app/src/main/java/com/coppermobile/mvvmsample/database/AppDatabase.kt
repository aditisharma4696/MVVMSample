package com.coppermobile.mvvmsample.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.coppermobile.mvvmsample.commentRoom.Comments
import com.coppermobile.mvvmsample.commentRoom.CommentsDAO
import com.coppermobile.mvvmsample.dishListRoom.Dish
import com.coppermobile.mvvmsample.dishListRoom.DishDAO

@Database(entities = arrayOf(Comments::class, Dish::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){

    abstract fun commentsDoaMethod(): CommentsDAO
    abstract fun dishDaoMethod(): DishDAO


    companion object {

        private var instance: AppDatabase? = null
        private const val DATABASE_NAME = "myDb"

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {}
                try {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
                } catch (ignored: Exception) {
                    Log.v("AppDatabase", ignored.toString())
                }
            }
            return instance!!
        }
    }
}