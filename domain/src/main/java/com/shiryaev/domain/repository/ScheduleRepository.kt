package com.shiryaev.domain.repository

import com.shiryaev.domain.model.Schedule
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single

interface ScheduleRepository {

    fun getSchedules(day: Int): Flowable<List<Schedule>>

    fun insertSchedules(schedules: List<Schedule>)
}