package com.shiryaev.domain.models

import androidx.room.ColumnInfo
import com.shiryaev.domain.utils.UtilsTableSchedule

class TimeAndWeek(

        @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART)
        var mTimeStart: Int,

        @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_WEEK)
        var mWeek: Int
)