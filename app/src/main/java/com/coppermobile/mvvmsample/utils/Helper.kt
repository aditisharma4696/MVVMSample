package com.coppermobile.mvvmsample.utils

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.text.format.DateFormat
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.util.*

class Helper {
    companion object {
        fun formatDate(): String {
            return DateFormat.format("MMM d, yy ", Date()) as String
        }

        /**
         * to hide keyboard
         */
        fun hideKbd(activity: FragmentActivity, view: View?) {
            if (activity != null && view != null) {
                val imm: InputMethodManager? = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm?.hideSoftInputFromWindow(view?.windowToken, 0)
            }
        }
    }
}