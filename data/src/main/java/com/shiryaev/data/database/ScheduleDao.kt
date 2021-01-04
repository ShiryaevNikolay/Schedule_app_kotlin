package com.shiryaev.data.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shiryaev.domain.model.Schedule
import com.shiryaev.domain.utils.UtilsDb

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM ${UtilsDb.TABLE_SCHEDULE} WHERE ${UtilsDb.SCHEDULE_COLUMN_DAY} = :mDay ORDERED BY ${UtilsDb.SCHEDULE_COLUMN_TIMESTART}" )
    fun getAllByDay(mDay: Int) : LiveData<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSchedule(schedule: Schedule)

    @Update
    fun updateSchedule(schedule: Schedule)

    @Delete
    fun deleteSchedule(schedule: Schedule)
}