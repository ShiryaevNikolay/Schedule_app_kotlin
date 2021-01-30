package com.shiryaev.schedule.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.Week
import com.shiryaev.schedule.R
import kotlinx.android.synthetic.main.custom_card_schedule.view.*

@SuppressLint("ViewConstructor")
class CustomItemSchedule(
        context: Context,
        private val listWeek: List<Week>
) : FrameLayout(context) {

    var onClickListener: ((Schedule) -> Unit)? = null
    var onLongClickListener: ((Schedule) -> Unit)? = null
    private var itemSchedule: Schedule? = null

    private val lessonTv: AppCompatTextView
    private val teacherTv: AppCompatTextView
    private val auditTv: AppCompatTextView
    private val weekTv: AppCompatTextView
    private val examTv: AppCompatTextView
    private val mIndicatorCardWeek: MaterialCardView
    private val mIndicatorWeek: View

    init {
        inflate(context, R.layout.custom_card_schedule, this)

        lessonTv = this.findViewById(R.id.lesson_schedule_tv)
        teacherTv = this.findViewById(R.id.teacher_schedule_tv)
        auditTv = this.findViewById(R.id.audit_schedule_tv)
        weekTv = this.findViewById(R.id.week_schedule_tv)
        examTv = this.findViewById(R.id.exam_schedule_tv)
        mIndicatorCardWeek = this.findViewById(R.id.indicator_card_week)
        mIndicatorWeek = this.findViewById(R.id.indicator_week)
    }

    fun setItemSchedule(data: Schedule) {
        this.itemSchedule = data
        with(data) {
            lessonTv.text = mLesson
            teacherTv.text = mTeacher
            auditTv.text = mAudit
            weekTv.text = mWeek
            examTv.text = mExam
        }
        teacherTv.isVisible = data.mTeacher != null
        auditTv.isVisible = data.mAudit != null
        examTv.isVisible = data.mExam != null

        mIndicatorCardWeek.isVisible = data.mWeek != ""

        if (data.mWeek == "") {
            mIndicatorWeek.setBackgroundColor(Color.TRANSPARENT)
            mIndicatorCardWeek.isVisible = false
        } else {
            listWeek.forEach { week ->
                if (data.mWeek == week.mName) {
                    if (week.mColor == "") {
                        val typedValue = TypedValue()
                        val typedArray: TypedArray = context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorAccent))
                        val color = typedArray.getColor(0, 0)
                        typedArray.recycle()
                        mIndicatorCardWeek.background.setTint(color)
                        mIndicatorWeek.setBackgroundColor(color)
                    } else {
                        mIndicatorCardWeek.background.setTint(context.resources.getIntArray(R.array.color_pick)[week.mColor.toInt()])
                        mIndicatorWeek.setBackgroundColor(context.resources.getIntArray(R.array.color_pick)[week.mColor.toInt()])
                    }
                    mIndicatorCardWeek.isVisible = true
                }
            }
        }

        this.item_card.setOnClickListener { onClickListener?.invoke(data) }
        this.item_card.setOnLongClickListener {
            onLongClickListener?.invoke(data)
            true
        }
    }
}