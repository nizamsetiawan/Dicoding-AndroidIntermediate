// DialogHelper.kt
package com.example.storysnap.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.storysnap.MainActivity

object DialogHelper {
    fun showSuccessDialog(context: Context, title: String, message: String) {
        SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
            .setTitleText(title)
            .setContentText(message)
            .setConfirmClickListener { sweetAlertDialog ->
                sweetAlertDialog.dismissWithAnimation()
                val intent = Intent(context, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
            .show()
    }

    fun showErrorDialog(context: Context, title: String, errorMessage: String) {
        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
            .setTitleText(title)
            .setContentText(errorMessage)
            .show()
    }

    fun showLoadingDialog(context: Context, title: String) {
        SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
            .setTitleText(title)
            .setCancelable(false)

    }

    fun dismissLoadingDialog() {
        // Tidak perlu melakukan apa-apa di sini karena SweetAlertDialog yang ditampilkan tidak disimpan dalam objek.
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
