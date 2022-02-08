package com.solucionestpvpos.themoviesegc.util

import android.app.Activity
import com.solucionestpvpos.themoviesegc.R

class UIUtil {

    companion object {
        @JvmStatic
        fun popError(
            activity: Activity,
            title: String,
            message: String, 
            callback: (isTryAgain: Boolean) -> Unit
        ) {
            val materialAlertDialog = MaterialAlertDialog(activity)
            materialAlertDialog.setTitle(title)
            materialAlertDialog.setMessage(message)
            materialAlertDialog.setPositiveClick(activity.getString(R.string.try_again)) {
                it.dismiss()
                callback.invoke(true)
            }
            materialAlertDialog.setNegativeClick(activity.getString(R.string.close)) {
                it.dismiss()
                callback.invoke(false)
            }
            materialAlertDialog.show()
        }

    }
}