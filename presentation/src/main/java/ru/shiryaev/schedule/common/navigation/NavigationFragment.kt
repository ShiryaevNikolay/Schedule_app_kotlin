package ru.shiryaev.schedule.common.navigation

import android.os.Bundle
import androidx.navigation.NavController
import ru.shiryaev.schedule.R

object NavigationFragment {

    fun navigate(controller: NavController, fragmentRoute: FragmentRoute, bundle: Bundle) {
        when (fragmentRoute) {
            is CreateScheduleFragmentRoute -> {
                controller.navigate(
                    R.id.action_mainScreen_to_addScheduleScreen,
                    bundle
                )
            }
            is CreateNoteFragmentRoute -> {
                controller.navigate(
                    R.id.action_mainScreen_to_addNoteScreen,
                    bundle
                )
            }
        }
    }
}