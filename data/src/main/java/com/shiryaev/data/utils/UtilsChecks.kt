package com.shiryaev.data.utils

import com.shiryaev.domain.models.TimeAndWeek

object UtilsChecks {

    fun checkTime(week: Int, selectedTime: Int, listTime: ArrayList<TimeAndWeek>) : Boolean {
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
}