package ru.shiryaev.schedule.common.navigation

sealed class FragmentRoute

data class CreateScheduleFragmentRoute(val fragmentClass: FragmentClass) : FragmentRoute()
//data class EditScheduleFragmentRoute(val fragmentClass: FragmentClass) : FragmentRoute()

data class CreateNoteFragmentRoute(val fragmentClass: FragmentClass) : FragmentRoute()
//data class EditNoteFragmentRoute(val fragmentClass: FragmentClass) : FragmentRoute()
