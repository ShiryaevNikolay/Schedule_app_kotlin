package com.shiryaev.data.database.schedule

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.domain.utils.UtilsTable

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM ${UtilsTable.TABLE_SCHEDULE} WHERE ${UtilsTable.SCHEDULE_COLUMN_DAY} = :mDay ORDER BY ${UtilsTable.SCHEDULE_COLUMN_TIMESTART}" )
    fun getAllByDay(mDay: Int) : LiveData<List<Schedule>>

    @Query("SELECT ${UtilsTable.SCHEDULE_COLUMN_LESSON} FROM ${UtilsTable.TABLE_SCHEDULE} ORDER BY ${UtilsTable.SCHEDULE_COLUMN_LESSON}" )
    fun getLessons() : LiveData<List<String>>

    @Query("SELECT ${UtilsTable.SCHEDULE_COLUMN_TEACHER} FROM ${UtilsTable.TABLE_SCHEDULE} ORDER BY ${UtilsTable.SCHEDULE_COLUMN_TEACHER}" )
    fun getTeachers() : LiveData<List<String>>

    @Query("SELECT ${UtilsTable.SCHEDULE_COLUMN_AUDIT} FROM ${UtilsTable.TABLE_SCHEDULE} ORDER BY ${UtilsTable.SCHEDULE_COLUMN_AUDIT}" )
    fun getAudits() : LiveData<List<String>>

    @Query("SELECT ${UtilsTable.SCHEDULE_COLUMN_EXAM} FROM ${UtilsTable.TABLE_SCHEDULE} ORDER BY ${UtilsTable.SCHEDULE_COLUMN_EXAM}" )
    fun getExams() : LiveData<List<String>>

    @Query("SELECT ${UtilsTable.SCHEDULE_COLUMN_TIMESTART} FROM ${UtilsTable.TABLE_SCHEDULE} ORDER BY ${UtilsTable.SCHEDULE_COLUMN_TIMESTART}" )
    fun getTimeStart() : LiveData<List<Int>>

    @Query("SELECT ${UtilsTable.SCHEDULE_COLUMN_TIMESTART}, ${UtilsTable.SCHEDULE_COLUMN_WEEK} " +
            "FROM ${UtilsTable.TABLE_SCHEDULE} " +
            "WHERE ${UtilsTable.SCHEDULE_COLUMN_DAY} = :mDay " +
            "ORDER BY ${UtilsTable.SCHEDULE_COLUMN_TIMESTART}" )
    fun getTimeStartByDay(mDay: Int) : LiveData<List<TimeAndWeek>>

    @Query("UPDATE ${UtilsTable.TABLE_SCHEDULE} " +
            "SET ${UtilsTable.SCHEDULE_COLUMN_WEEK} = :newNameWeek " +
            "WHERE ${UtilsTable.SCHEDULE_COLUMN_WEEK} = :oldNameWeek")
    fun updateWeekSchedule(oldNameWeek: String, newNameWeek: String)

    @Query("UPDATE ${UtilsTable.TABLE_SCHEDULE} " +
            "SET ${UtilsTable.SCHEDULE_COLUMN_WEEK} = '' " +
            "WHERE ${UtilsTable.SCHEDULE_COLUMN_WEEK} = :nameWeek")
    fun deleteWeekSchedule(nameWeek: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(schedule: Schedule)

    @Update
    fun updateSchedule(schedule: Schedule)

    @Delete
    fun deleteSchedule(schedule: Schedule)
}