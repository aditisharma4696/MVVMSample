package com.coppermobile.mvvmsample.commentRoom

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface CommentsDAO {

    @Insert
    fun insertData(vararg comments: Comments)

    @Delete
    fun deleteData(comments: Comments)

    @Query("Select * from comments_table where dish_id=:dishId order by comment_time desc")
    fun getComments(dishId: Int): LiveData<List<Comments>>
}