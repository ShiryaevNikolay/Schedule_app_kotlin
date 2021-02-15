package ru.shiryaev.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.shiryaev.data.database.note.NoteDao
import ru.shiryaev.data.database.schedule.ScheduleDao
import ru.shiryaev.data.database.week.WeekDao
import ru.shiryaev.domain.models.Note
import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.Week

@Database(version = 1, entities = [Schedule::class, Week::class, Note::class])
abstract class Storage : RoomDatabase() {
    abstract fun getScheduleDao(): ScheduleDao
    abstract fun getWeekDao(): WeekDao
    abstract fun getNoteDao(): NoteDao
}