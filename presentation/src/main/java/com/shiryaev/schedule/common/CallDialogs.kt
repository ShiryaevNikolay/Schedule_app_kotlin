package com.shiryaev.schedule.common

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.Toast
import com.shiryaev.data.utils.UtilsChecks
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.schedule.R
import java.util.*
import kotlin.collections.ArrayList

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

    fun callDatePickerDialog(context: Context, onSelectDate: (String) -> Unit) {
        val dayCurrent = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        val monthCurrent = Calendar.getInstance().get(Calendar.MONTH)
        val yearCurrent = Calendar.getInstance().get(Calendar.YEAR)
        DatePickerDialog(context, { _, year, month, dayOfMonth ->
            var selectedDate: String = if (dayOfMonth < 10) "0$dayOfMonth, " else "$dayOfMonth, "
            selectedDate += context.resources.getStringArray(R.array.month)[month]
            selectedDate += ", $year"
            onSelectDate.invoke(selectedDate)
        }, yearCurrent, monthCurrent, dayCurrent).apply {
            window?.setBackgroundDrawableResource(R.drawable.alert_dialog_bg)
        }.show()
    }
}