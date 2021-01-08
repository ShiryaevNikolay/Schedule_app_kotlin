package com.shiryaev.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shiryaev.domain.utils.UtilsTableScheulde

@Entity(tableName = UtilsTableScheulde.TABLE_SCHEDULE)
class Schedule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_ID)
    var mId: Long = 0,

    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_LESSON)
    var mLesson: String,

    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_TEACHER)
    var mTeacher: String,

    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_AUDIT)
    var mAudit: String,

    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_TIMESTART)
    var mTimeStart: Int,

//    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_TIMEEND)
//    var mTimeEnd: Int,

    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_WEEK)
    var mWeek: Int,

    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_DAY)
    var mDay: Int,

    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_EXAM)
    var mExam: String? = null
)