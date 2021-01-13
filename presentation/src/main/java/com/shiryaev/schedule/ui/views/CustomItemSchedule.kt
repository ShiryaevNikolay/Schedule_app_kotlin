package com.shiryaev.schedule.ui.views

import android.content.Context
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import com.shiryaev.domain.models.Schedule
import com.shiryaev.schedule.R
import kotlinx.android.synthetic.main.custom_card_schedule.view.*

class CustomItemSchedule(
        context: Context

) : FrameLayout(context) {

    var onClickListener: ((Schedule) -> Unit)? = null
    private var itemSchedule: Schedule? = null

    private val lessonTv: AppCompatTextView
    private val teacherTv: AppCompatTextView
    private val auditTv: AppCompatTextView
    private val weekTv: AppCompatTextView
    private val examTv: AppCompatTextView

    init {
        inflate(context, R.layout.custom_card_schedule, this)

        lessonTv = this.findViewById(R.id.lesson_schedule_tv)
        teacherTv = this.findViewById(R.id.teacher_schedule_tv)
        auditTv = this.findViewById(R.id.audit_schedule_tv)
        weekTv = this.findViewById(R.id.week_schedule_tv)
        examTv = this.findViewById(R.id.exam_schedule_tv)

        this.item_card.setOnClickListener { itemSchedule?.let { it1 -> onClickListener?.invoke(it1) } }
    }

    fun setItemSchedule(data: Schedule) {
        this.itemSchedule = data
        with(data) {
            lessonTv.text = mLesson
            teacherTv.text = mTeacher
            auditTv.text = mAudit
            weekTv.text = mWeek.toString()
            examTv.text = mExam
        }
    }
}