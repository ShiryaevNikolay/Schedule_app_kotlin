package com.shiryaev.data.database

import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.week.WeekDao
import com.shiryaev.domain.models.Week
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class WeekRepository {

    @Inject
    lateinit var mStorage: Storage

    private var mWeekDao: WeekDao

    init {
        AppDelegate.getAppComponent().injectWeekRepository(this)
        mWeekDao = mStorage.getWeekDao()
    }

    fun getWeeks() = mWeekDao.getAllWeek()

    fun getCountWeek() = mWeekDao.getCountWeek()

    fun insertWeek(week: Week) = Completable.fromRunnable { mWeekDao.insertWeek(week) }
                .subscribeOn(Schedulers.io())

    fun updateWeek(week: Week) = Completable.fromRunnable { mWeekDao.updateWeek(week) }
                .subscribeOn(Schedulers.io())

    fun deleteWeek(week: Week) = Completable.fromRunnable { mWeekDao.deleteWeek(week) }
                .subscribeOn(Schedulers.io())
}