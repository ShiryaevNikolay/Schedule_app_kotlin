package com.shiryaev.data.common

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult

object Transfer {
    fun transferToActivity(fromActivity: AppCompatActivity, toActivity: AppCompatActivity, requestCode: Int) {
        val intent = Intent(fromActivity, toActivity::class.java)
        startActivityForResult(fromActivity, intent, requestCode, null)
    }
}