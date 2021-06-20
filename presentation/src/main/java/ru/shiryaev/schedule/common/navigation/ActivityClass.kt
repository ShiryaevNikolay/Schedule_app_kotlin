package ru.shiryaev.schedule.common.navigation

import ru.shiryaev.schedule.ui.fragments.screens.AddNoteScreenFragment
import ru.shiryaev.schedule.ui.fragments.screens.AddScheduleScreenFragment

enum class FragmentClass(val fragment: Class<*>) {
    CREATE_SCHEDULE(AddScheduleScreenFragment::class.java),
    EDIT_SCHEDULE(AddScheduleScreenFragment::class.java),
    CREATE_NOTE(AddNoteScreenFragment::class.java),
    EDIT_NOTE(AddNoteScreenFragment::class.java)
}