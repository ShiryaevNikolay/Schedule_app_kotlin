package com.shiryaev.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shiryaev.domain.utils.UtilsDb

@Entity(tableName = UtilsDb.TABLE_SCHEDULE)
class Schedule(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_ID)
    var mId: Long = 0,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_LESSON)
    var mLesson: String,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_TEACHER)
    var mTeacher: String,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_AUDIT)
    var mAudit: String,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_TIMESTART)
    var mTimeStart: Int,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_TIMEEND)
    var mTimeEnd: Int,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_WEEK)
    var mWeek: Int,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_DAY)
    var mDay: Int,

    @ColumnInfo(name = UtilsDb.SCHEDULE_COLUMN_EXAM)
    var mExam: String? = null
)