package com.shiryaev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shiryaev.data.database.schedule.ScheduleDao
import com.shiryaev.data.database.week.WeekDao
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.Week

@Database(version = 1, entities = [Schedule::class, Week::class])
abstract class Storage : RoomDatabase() {
    abstract fun getScheduleDao(): ScheduleDao
    abstract fun getWeekDao(): WeekDao
}