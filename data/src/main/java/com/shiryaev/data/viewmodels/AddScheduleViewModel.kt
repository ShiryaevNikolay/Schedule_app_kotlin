package com.shiryaev.data.viewmodels

import androidx.lifecycle.ViewModel
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.Repository
import com.shiryaev.domain.models.Schedule
import javax.inject.Inject

class AddScheduleViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: Repository

    init {
        AppDelegate.getAppComponent().injectAddScheduleViewModel(this)
    }

    fun getTimeStartByDay(mDay: Int) = mRepository.getTimeStartByDay(mDay)

    fun insertSchedule(schedule: Schedule) { mRepository.insertSchedule(schedule) }
}