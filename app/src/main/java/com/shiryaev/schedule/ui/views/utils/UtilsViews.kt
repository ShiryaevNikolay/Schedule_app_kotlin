package com.shiryaev.schedule.ui.views.utils

import android.content.Context
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import com.shiryaev.schedule.R

//fun changeIconCalendar(context: Context, iconBtn: ImageButton, calendar: Boolean) : Boolean {
//    if (calendar) {
//        iconBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_format_list_bulleted))
//    } else {
//        iconBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_calendar_month))
//    }
//    return calendar
//}

fun showTabs(context: Context, iconBtn: ImageButton, show: Boolean) : Boolean {
    if (show) {
        iconBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_up))
    } else {
        iconBtn.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_chevron_down))
    }
    return show
}