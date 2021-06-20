package ru.shiryaev.schedule.common.navigation

enum class ActivityClass(val activity: Class<*>) {
    CREATE_SCHEDULE(AddScheduleActivity::class.java),
    EDIT_SCHEDULE(AddScheduleActivity::class.java),
    CREATE_NOTE(AddNoteActivity::class.java),
    EDIT_NOTE(AddNoteActivity::class.java)
}