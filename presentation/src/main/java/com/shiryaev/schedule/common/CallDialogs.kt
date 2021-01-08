package com.shiryaev.schedule.common

import android.app.TimePickerDialog
import android.content.Context
import com.shiryaev.data.utils.UtilsChecks
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.schedule.R

object CallDialogs {

    fun callTimePicker(context: Context, week: Int, listTime: ArrayList<TimeAndWeek>, onSelectTime: (Int, Int) -> Unit) {
        var selectedTime = 0
        TimePickerDialog(context, { _, hourOfDay, minute ->
            if (UtilsChecks.checkTime(week, ("$hourOfDay$minute").toInt(), listTime)) {
                onSelectTime.invoke(hourOfDay, minute)
            }
        }, 12, 0, true).apply {
            window?.setBackgroundDrawableResource(R.drawable.alert_dialog_bg)
        }.show()
    }
}