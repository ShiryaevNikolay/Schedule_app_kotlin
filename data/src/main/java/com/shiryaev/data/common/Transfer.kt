package com.shiryaev.data.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.domain.utils.UtilsTable

object Transfer {
    fun transferToActivity(fromActivity: AppCompatActivity, toActivity: AppCompatActivity, options: Bundle? = null) {
        val intent = Intent(fromActivity, toActivity::class.java).apply {
            if (options != null) {
                putExtra(UtilsKeys.REQUEST_CODE.name, options.getString(UtilsKeys.REQUEST_CODE.name))
                putExtra(UtilsKeys.POSITION_PAGE.name, options.getInt(UtilsKeys.POSITION_PAGE.name))
                putExtra(UtilsTable.SCHEDULE, options.getSerializable(UtilsTable.SCHEDULE))
            }
        }
        startActivity(fromActivity, intent, options)
    }
}