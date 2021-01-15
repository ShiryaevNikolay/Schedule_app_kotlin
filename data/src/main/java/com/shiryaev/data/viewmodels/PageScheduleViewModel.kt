package com.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.Repository
import com.shiryaev.domain.models.Schedule
import javax.inject.Inject

class PageScheduleViewModel : ViewModel() {

    @Inject
    lateinit var mRepository: Repository

    private val mIsLoading = MutableLiveData<Boolean>()
    private val mIsErrorVisible = MutableLiveData<Boolean>()

    init {
        AppDelegate.getAppComponent().injectPageScheduleViewModel(this)
    }

    fun getIsLoading() = mIsLoading

    fun getIsErrorVisible() = mIsErrorVisible

    fun setIsErrorVisible(value: Boolean) { mIsErrorVisible.value = value }

    fun setIsLoading(value: Boolean) { mIsLoading.postValue(value) }

    fun getSchedules(mDay: Int) = mRepository.getSchedules(mDay)

    fun deleteSchedule(schedule: Schedule) {
        mRepository.deleteSchedule(schedule)
            .doOnSubscribe { mIsLoading.postValue(true) }
            .doFinally { mIsLoading.postValue(false) }
            .subscribe {  }
    }
}