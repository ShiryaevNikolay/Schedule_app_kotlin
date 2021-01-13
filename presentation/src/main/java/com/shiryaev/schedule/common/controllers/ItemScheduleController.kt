package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import com.shiryaev.domain.models.Schedule
import com.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemScheduleController(
        val onClickListener: () -> Unit
) : BindableItemController<Schedule, ItemScheduleController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Schedule) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Schedule>(parent, R.layout.custom_card_schedule) {

        private val lessonTv: AppCompatTextView = itemView.findViewById(R.id.lesson_schedule_tv)
        private val teacherTv: AppCompatTextView = itemView.findViewById(R.id.teacher_schedule_tv)
        private val auditTv: AppCompatTextView = itemView.findViewById(R.id.audit_schedule_tv)
        private val weekTv: AppCompatTextView = itemView.findViewById(R.id.week_schedule_tv)
        private val examTv: AppCompatTextView = itemView.findViewById(R.id.exam_schedule_tv)

        init {
            itemView.setOnClickListener { onClickListener.invoke() }
        }

        override fun bind(data: Schedule) {
            with(data) {
                lessonTv.text = mLesson
                teacherTv.text = mTeacher
                auditTv.text = mAudit
                weekTv.text = mWeek.toString()
                examTv.text = mExam
            }
        }
    }
}