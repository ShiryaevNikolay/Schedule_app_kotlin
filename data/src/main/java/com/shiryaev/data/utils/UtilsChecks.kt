package com.shiryaev.data.utils

import com.shiryaev.domain.models.TimeAndWeek

object UtilsChecks {

    const val TIME_DISABLE = -1

    fun checkTime(week: String, selectedTime: Int, listTime: ArrayList<TimeAndWeek>) : Boolean {
        var flag = true
        for (i in 0 until listTime.size) {
            if (listTime[i].mTimeStart == selectedTime) {
                if (listTime[i].mWeek == 0)  flag = false
//                else for () {
//
//                }
            }
        }
        return flag
    }

    fun checkAddSchedule(lesson: String, time: Int) =  (lesson.isNotBlank() || lesson != "") && time != TIME_DISABLE
}