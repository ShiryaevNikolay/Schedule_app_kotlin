package com.shiryaev.data.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.shiryaev.domain.utils.UtilsKeys

object Transfer {
    fun transferToActivity(fromActivity: AppCompatActivity, toActivity: AppCompatActivity, requestCode: Int, options: Bundle? = null) {
        val intent = Intent(fromActivity, toActivity::class.java).apply {
            if (options != null) {
                putExtra(UtilsKeys.POSITION_PAGE.key, options.getInt(UtilsKeys.POSITION_PAGE.key))
            }
        }
        startActivityForResult(fromActivity, intent, requestCode, options)
    }
}