package ru.shiryaev.data.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.shiryaev.domain.utils.UtilsKeys
import ru.shiryaev.domain.utils.UtilsTable

object Transfer {
    fun transferToActivity(fromActivity: Context, toActivity: Class<*>, options: Bundle? = null) {
        val intent = Intent(fromActivity, toActivity).apply {
            if (options != null) {
                putExtra(UtilsKeys.REQUEST_CODE.name, options.getString(UtilsKeys.REQUEST_CODE.name))
                putExtra(UtilsKeys.POSITION_PAGE.name, options.getInt(UtilsKeys.POSITION_PAGE.name))
                putExtra(UtilsTable.SCHEDULE, options.getSerializable(UtilsTable.SCHEDULE))
                putExtra(UtilsTable.NOTE, options.getSerializable(UtilsTable.NOTE))
            }
        }
        fromActivity.startActivity(intent, options)
    }
}