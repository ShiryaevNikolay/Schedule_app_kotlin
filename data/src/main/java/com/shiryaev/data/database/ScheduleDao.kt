package com.shiryaev.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.domain.utils.UtilsTableSchedule

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM ${UtilsTableSchedule.TABLE_SCHEDULE} WHERE ${UtilsTableSchedule.SCHEDULE_COLUMN_DAY} = :mDay ORDER BY ${UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART}" )
    fun getAllByDay(mDay: Int) : LiveData<List<Schedule>>

    @Query("SELECT DISTINCT ${UtilsTableSchedule.SCHEDULE_COLUMN_LESSON} FROM ${UtilsTableSchedule.TABLE_SCHEDULE}")
    fun getLessons() : LiveData<List<String>>

    @Query("SELECT DISTINCT ${UtilsTableSchedule.SCHEDULE_COLUMN_TEACHER} FROM ${UtilsTableSchedule.TABLE_SCHEDULE}")
    fun getTeachers() : LiveData<List<String>>

    @Query("SELECT DISTINCT ${UtilsTableSchedule.SCHEDULE_COLUMN_AUDIT} FROM ${UtilsTableSchedule.TABLE_SCHEDULE}")
    fun getAudits() : LiveData<List<String>>

    @Query("SELECT DISTINCT ${UtilsTableSchedule.SCHEDULE_COLUMN_EXAM} FROM ${UtilsTableSchedule.TABLE_SCHEDULE}")
    fun getExams() : LiveData<List<String>>

    @Query("SELECT DISTINCT ${UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART} FROM ${UtilsTableSchedule.TABLE_SCHEDULE} ORDER BY ${UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART}")
    fun getTimeStart() : LiveData<List<Int>>

    @Query("SELECT ${UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART}, ${UtilsTableSchedule.SCHEDULE_COLUMN_WEEK} " +
            "FROM ${UtilsTableSchedule.TABLE_SCHEDULE} " +
            "WHERE ${UtilsTableSchedule.SCHEDULE_COLUMN_DAY} = :mDay " +
            "ORDER BY ${UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART}")
    fun getTimeStartByDay(mDay: Int) : LiveData<List<TimeAndWeek>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(schedule: Schedule)

    @Update
    fun updateSchedule(schedule: Schedule)

    @Delete
    fun deleteSchedule(schedule: Schedule)
}