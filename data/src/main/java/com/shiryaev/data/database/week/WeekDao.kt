package com.shiryaev.data.database.week

import androidx.lifecycle.LiveData
import androidx.room.*
import com.shiryaev.domain.models.Week
import com.shiryaev.domain.utils.UtilsTable

@Dao
interface WeekDao {
    @Query("SELECT * FROM ${UtilsTable.TABLE_WEEK}")
    fun getAllWeek(): LiveData<List<Week>>

    @Query("SELECT COUNT(*) FROM ${UtilsTable.TABLE_WEEK}")
    fun getCountWeek(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWeek(week: Week)

    @Update
    fun updateWeek(week: Week)

    @Delete
    fun deleteWeek(week: Week)
}