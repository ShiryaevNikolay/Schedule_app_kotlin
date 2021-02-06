package com.shiryaev.data.database.repository

import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.Storage
import com.shiryaev.data.database.schedule.ScheduleDao
import com.shiryaev.data.database.week.WeekDao
import com.shiryaev.domain.models.Schedule
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ScheduleRepository {

    private var mScheduleDao: ScheduleDao
    private var mWeekDao: WeekDao

    @Inject
    lateinit var mStorage: Storage

    init {
        AppDelegate.getAppComponent().injectScheduleRepository(this)
        mScheduleDao = mStorage.getScheduleDao()
        mWeekDao = mStorage.getWeekDao()
    }

    fun getWeeks() = mWeekDao.getAllWeek()

    fun getSchedules(day: Int) = mScheduleDao.getAllByDay(day)

    fun getSchedules(day: Int, week: String) = mScheduleDao.getAllByDayForWeek(day, week)

    fun getLessons() = mScheduleDao.getLessons()

    fun getTeachers() = mScheduleDao.getTeachers()

    fun getAudits() = mScheduleDao.getAudits()

    fun getExams() = mScheduleDao.getExams()

    fun getTimeStart() = mScheduleDao.getTimeStart()

    fun getTimeStartByDay(mDay: Int) = mScheduleDao.getTimeStartByDay(mDay)

    fun insertSchedule(schedule: Schedule) {
        Completable.fromRunnable { mScheduleDao.insertSchedule(schedule) }
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    fun updateSchedule(schedule: Schedule) {
        Completable.fromRunnable { mScheduleDao.updateSchedule(schedule) }
            .subscribeOn(Schedulers.io())
            .subscribe()
    }

    fun deleteSchedule(schedule: Schedule): Completable = Completable.fromRunnable { mScheduleDao.deleteSchedule(schedule) }
            .subscribeOn(Schedulers.io())
}