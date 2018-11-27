package com.coppermobile.mvvmsample.utils

import android.view.View
import com.coppermobile.mvvmsample.commentRoom.Comments

interface IShowPopUpListener {
     fun showPopupMenu(comments: Comments, position: Int, view: View)
}