package com.shiryaev.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shiryaev.domain.utils.UtilsTableSchedule

@Entity(tableName = UtilsTableSchedule.TABLE_SCHEDULE)
class Schedule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_ID)
    var mId: Long = 0,

    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_LESSON)
    var mLesson: String,

    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_TEACHER)
    var mTeacher: String,

    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_AUDIT)
    var mAudit: String,

    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART)
    var mTimeStart: Int,

//    @ColumnInfo(name = UtilsTableScheulde.SCHEDULE_COLUMN_TIMEEND)
//    var mTimeEnd: Int,

    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_WEEK)
    var mWeek: Int,

    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_DAY)
    var mDay: Int,

    @ColumnInfo(name = UtilsTableSchedule.SCHEDULE_COLUMN_EXAM)
    var mExam: String? = null
)