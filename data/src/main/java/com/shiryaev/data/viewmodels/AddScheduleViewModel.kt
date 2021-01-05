package com.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.Repository
import com.shiryaev.domain.model.Schedule
import javax.inject.Inject

class ScheduleViewModel : ViewModel() {

    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsErrorVisible = MutableLiveData<Boolean>()
    private val mSchedules = MutableLiveData<List<Schedule>>()

    @Inject
    lateinit var mRepository: Repository

    init {
        AppDelegate.getAppComponent().injectScheduleViewModel(this)
        mSchedules.value = ArrayList()
        loadSchedules()
    }

    fun getIsLoading() = mIsLoading

    fun getIsErrorVisible() = mIsErrorVisible

    private fun loadSchedules() {

    }
}