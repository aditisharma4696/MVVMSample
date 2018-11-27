package com.coppermobile.mvvmsample.dishdetail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.coppermobile.mvvmsample.commentRoom.Comments

class CommentsViewModel(application: Application) : AndroidViewModel(application) {


    fun getAllComments(dishId: Int): LiveData<List<Comments>>? {
        return CommentRepository.getInstance(getApplication()).getComments(dishId)
    }

    fun deleteComment(comments: Comments) {
        CommentRepository.getInstance(getApplication()).deleteData(comments)
    }

    fun insertComment(comments: Comments) {
        CommentRepository.getInstance(getApplication()).insertData(comments)
    }
}