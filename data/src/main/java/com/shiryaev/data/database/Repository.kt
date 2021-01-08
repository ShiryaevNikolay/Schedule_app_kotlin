package com.shiryaev.data.database

import com.shiryaev.data.AppDelegate
import com.shiryaev.domain.models.Schedule
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class Repository {

    private var mScheduleDao: ScheduleDao

    @Inject
    lateinit var mStorage: Storage

    init {
        AppDelegate.getAppComponent().injectRepository(this)
        mScheduleDao = mStorage.getScheduleDao()
    }

    fun getSchedules(day: Int) = mScheduleDao.getAllByDay(day)

    fun getLessons() = mScheduleDao.getLessons()

    fun getTeachers() = mScheduleDao.getTeachers()

    fun getAudits() = mScheduleDao.getAudits()

    fun getExams() = mScheduleDao.getExams()

    fun getTimeStartByDay(mDay: Int) = mScheduleDao.getTimeStartByDay(mDay)

    fun insertSchedule(schedule: Schedule) {
        Observable.fromCallable { mScheduleDao.insertSchedule(schedule) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}