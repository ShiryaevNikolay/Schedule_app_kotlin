package com.shiryaev.domain.repository

import com.shiryaev.domain.model.Schedule
import io.reactivex.rxjava3.core.Single

interface ScheduleRepository {

    fun getSchedules(): Single<List<Schedule>>

    fun insertSchedules(schedules: List<Schedule>)
}