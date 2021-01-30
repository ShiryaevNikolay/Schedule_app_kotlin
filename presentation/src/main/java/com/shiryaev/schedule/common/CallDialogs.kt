package com.shiryaev.schedule.common

import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import com.shiryaev.data.utils.UtilsChecks
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.schedule.R

object CallDialogs {

    fun callTimePicker(context: Context, week: String, listTime: ArrayList<TimeAndWeek>, onSelectTime: (Int, Int) -> Unit) {
        TimePickerDialog(context, { _, hourOfDay, minute ->
            val selectedTime: String = if (minute < 10) "${hourOfDay}0$minute" else "$hourOfDay$minute"
            if (UtilsChecks.checkTime(week, selectedTime.toInt(), listTime)) {
                onSelectTime.invoke(hourOfDay, minute)
            } else {
                Toast.makeText(context, "В это время уже есть занятие", Toast.LENGTH_SHORT).show()
            }
        }, 12, 0, true).apply {
            window?.setBackgroundDrawableResource(R.drawable.alert_dialog_bg)
        }.show()
    }
}