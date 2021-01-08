package com.shiryaev.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.domain.utils.UtilsTableScheulde

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM ${UtilsTableScheulde.TABLE_SCHEDULE} WHERE ${UtilsTableScheulde.SCHEDULE_COLUMN_DAY} = :mDay ORDER BY ${UtilsTableScheulde.SCHEDULE_COLUMN_TIMESTART}" )
    fun getAllByDay(mDay: Int) : LiveData<List<Schedule>>

    @Query("SELECT DISTINCT ${UtilsTableScheulde.SCHEDULE_COLUMN_LESSON} FROM ${UtilsTableScheulde.TABLE_SCHEDULE}")
    fun getLessons() : LiveData<List<String>>

    @Query("SELECT DISTINCT ${UtilsTableScheulde.SCHEDULE_COLUMN_TEACHER} FROM ${UtilsTableScheulde.TABLE_SCHEDULE}")
    fun getTeachers() : LiveData<List<String>>

    @Query("SELECT DISTINCT ${UtilsTableScheulde.SCHEDULE_COLUMN_AUDIT} FROM ${UtilsTableScheulde.TABLE_SCHEDULE}")
    fun getAudits() : LiveData<List<String>>

    @Query("SELECT DISTINCT ${UtilsTableScheulde.SCHEDULE_COLUMN_EXAM} FROM ${UtilsTableScheulde.TABLE_SCHEDULE}")
    fun getExams() : LiveData<List<String>>

//    @Query("SELECT DISTINCT ${UtilsTableScheulde.SCHEDULE_COLUMN_TIMESTART} FROM ${UtilsTableScheulde.TABLE_SCHEDULE} " +
//            "ORDER BY ${UtilsTableScheulde.SCHEDULE_COLUMN_TIMESTART}")
//    fun getTimeStart() : LiveData<List<Int>>

    @Query("SELECT ${UtilsTableScheulde.SCHEDULE_COLUMN_TIMESTART}, ${UtilsTableScheulde.SCHEDULE_COLUMN_WEEK} " +
            "FROM ${UtilsTableScheulde.TABLE_SCHEDULE} " +
            "WHERE ${UtilsTableScheulde.SCHEDULE_COLUMN_DAY} = :mDay " +
            "ORDER BY ${UtilsTableScheulde.SCHEDULE_COLUMN_TIMESTART}")
    fun getTimeStartByDay(mDay: Int) : LiveData<List<TimeAndWeek>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(schedule: Schedule)

    @Update
    fun updateSchedule(schedule: Schedule)

    @Delete
    fun deleteSchedule(schedule: Schedule)
}