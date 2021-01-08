package com.shiryaev.domain.models

import androidx.room.ColumnInfo
import com.shiryaev.domain.utils.UtilsTableScheulde

class TimeAndWeek(

        @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_TIMESTART)
        var mTimeStart: Int,

        @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_WEEK)
        var mWeek: Int
)