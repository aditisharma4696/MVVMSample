package com.coppermobile.mvvmsample.commentRoom

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "comments_table")
class Comments {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    var id: Int = 0

    @ColumnInfo(name = "comment_name")
    lateinit var comments: String

    @ColumnInfo(name = "comment_time")
    lateinit var date: String

    @ColumnInfo(name = "dish_id")
    var dishID: Int =0
}