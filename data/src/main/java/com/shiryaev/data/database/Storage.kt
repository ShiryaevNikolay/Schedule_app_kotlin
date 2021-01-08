package com.shiryaev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.shiryaev.domain.models.Schedule

@Database(version = 1, entities = [Schedule::class])
abstract class Storage : RoomDatabase() {
    abstract fun getScheduleDao(): ScheduleDao
}