package com.shiryaev.data.common

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.shiryaev.domain.utils.UtilsIntent
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.domain.utils.UtilsTableSchedule

object Transfer {
    fun transferToActivity(fromActivity: AppCompatActivity, toActivity: AppCompatActivity, options: Bundle? = null) {
        val intent = Intent(fromActivity, toActivity::class.java).apply {
            if (options != null) {
                putExtra(UtilsKeys.REQUEST_CODE.name, options.getString(UtilsKeys.REQUEST_CODE.name))
                putExtra(UtilsKeys.POSITION_PAGE.name, options.getInt(UtilsKeys.POSITION_PAGE.name))
                putExtra(UtilsTableSchedule.SCHEDULE, options.getSerializable(UtilsTableSchedule.SCHEDULE))
            }
        }
        startActivity(fromActivity, intent, options)
    }
}