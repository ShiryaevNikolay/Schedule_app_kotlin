package com.shiryaev.domain.utils

import java.text.SimpleDateFormat
import java.util.*

object UtilsConvert {
    fun format(time: Long): String {
        val date = Date(time + 1000L)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }

//    fun convertTimeIntToString(hour: Int, minute: Int) : String {
//        val hourString = if (hour < 10) "0$hour" else "$hour"
//        val minuteString = if (minute < 10) "0$minute" else "$minute"
//        return "$hourString:$minuteString"
//    }
    fun convertTimeIntToString(time: Int) : String {
        return if (time.length() == 3) {
            "0${time / 100}:${time % 100}"
        }
        else {
            "${time / 100}:${time % 100}"
        }
    }
}