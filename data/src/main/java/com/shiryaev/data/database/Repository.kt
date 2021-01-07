package com.shiryaev.data.database

import com.shiryaev.data.AppDelegate
import com.shiryaev.domain.model.Schedule
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

    fun getAllSchedules(day: Int) = mScheduleDao.getAllByDay(day)

    fun insertSchedule(schedule: Schedule) {
        Observable.fromCallable { mScheduleDao.insertSchedule(schedule) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}