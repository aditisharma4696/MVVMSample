package com.coppermobile.mvvmsample.dishdetail

import android.app.Application
import android.arch.lifecycle.LiveData
import com.coppermobile.mvvmsample.commentRoom.Comments
import com.coppermobile.mvvmsample.commentRoom.CommentsDAO
import com.coppermobile.mvvmsample.database.AppDatabase
import com.coppermobile.mvvmsample.utils.AppExecutor


class CommentRepository private constructor(application: Application) {

    private var commentsDao: CommentsDAO
    private var appExecutors: AppExecutor

    init {
        appExecutors = AppExecutor()
        val db = AppDatabase.getInstance(application)
        commentsDao = db.commentsDoaMethod()
    }


    fun insertData(comment: Comments) {
        var runnable = object : Runnable {
            override fun run() {
                commentsDao.insertData(comment)
            }
        }

        appExecutors.diskIo.execute(runnable)
    }

    fun deleteData(comment: Comments) {
        var runnable = object : Runnable {
            override fun run() {
                commentsDao.deleteData(comment)
            }
        }
        appExecutors.diskIo.execute(runnable)
    }

    fun getComments(id: Int): LiveData<List<Comments>>? {
        return commentsDao.getComments(id)
    }

    companion object {

        private var INSTANCE: CommentRepository? = null

        fun getInstance(application: Application): CommentRepository {
            if (INSTANCE == null) {
                synchronized(CommentRepository::class.java) {
                    INSTANCE = CommentRepository(application)
                }
            }
            return INSTANCE!!
        }
    }

}