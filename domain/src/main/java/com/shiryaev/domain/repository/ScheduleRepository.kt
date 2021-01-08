package com.shiryaev.domain.repository

import com.shiryaev.domain.models.Schedule
import io.reactivex.rxjava3.core.Flowable

interface ScheduleRepository {

    fun getSchedules(day: Int): Flowable<List<Schedule>>

    fun insertSchedules(schedules: List<Schedule>)
}