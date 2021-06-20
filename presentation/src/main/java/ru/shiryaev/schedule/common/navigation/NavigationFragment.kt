package ru.shiryaev.schedule.common.navigation

import android.os.Bundle
import androidx.navigation.NavController
import ru.shiryaev.schedule.R

object NavigationFragment {

    fun navigate(controller: NavController, fragmentRoute: FragmentRoute, bundle: Bundle) {
        when (fragmentRoute) {
            is CreateScheduleFragmentRoute,
            is EditScheduleFragmentRoute-> {
                controller.navigate(
                    R.id.action_mainScreen_to_addScheduleScreen,
                    bundle
                )
            }
            is CreateNoteFragmentRoute,
            is EditNoteFragmentRoute-> {
                controller.navigate(
                    R.id.action_mainScreen_to_addNoteScreen,
                    bundle
                )
            }
        }
    }
}