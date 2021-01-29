package com.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.Repository
import com.shiryaev.domain.models.Schedule
import javax.inject.Inject

class AddScheduleViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: Repository

    private val mFabIsVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectAddScheduleViewModel(this)
    }

    fun getWeeks() = mRepository.getWeeks()

    fun getListLessons() = mRepository.getLessons()

    fun getListTeachers() = mRepository.getTeachers()

    fun getListAudits() = mRepository.getAudits()

    fun getListExams() = mRepository.getExams()

    fun setFabIsVisible(value: Boolean) { mFabIsVisible.value = value }

    fun getFabIsVisible() = mFabIsVisible

    fun getListTimeStart() = mRepository.getTimeStart()

    fun getTimeStartByDay(mDay: Int) = mRepository.getTimeStartByDay(mDay)

    fun insertSchedule(schedule: Schedule) { mRepository.insertSchedule(schedule) }

    fun updateSchedule(schedule: Schedule) { mRepository.updateSchedule(schedule) }
}