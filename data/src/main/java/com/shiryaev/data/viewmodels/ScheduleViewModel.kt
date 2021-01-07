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
//    private val mLessonFiled = MutableLiveData<String>()
//    private val mTeacherFiled = MutableLiveData<String>()
//    private val mAuditFiled = MutableLiveData<String>()

    @Inject
    lateinit var mRepository: Repository

    init {
        AppDelegate.getAppComponent().injectScheduleViewModel(this)
        mSchedules.value = ArrayList()
    }

    fun getIsLoading() = mIsLoading

    fun getIsErrorVisible() = mIsErrorVisible

//    fun setLessonText(text: String) {
//        mLessonFiled.value = text
//    }

//    fun getLessonText() = mLessonFiled

//    override fun onCleared() {
//
//    }

    fun loadSchedules(day: Int) {

//        mRepository.getAllSchedules(day)
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnSubscribe { mIsLoading.postValue(true) }
//                .doFinally { mIsLoading.postValue(false) }
//                .subscribe {
//                    if (it == null || it.isEmpty()) {
//                        mIsErrorVisible.postValue(true)
//                    } else {
//                        mIsErrorVisible.postValue(false)
//                        mSchedules.postValue(it)
//                    }
//                }
    }

    fun insertSchedule(schedule: Schedule) {
        mRepository.insertSchedule(schedule)
    }
}