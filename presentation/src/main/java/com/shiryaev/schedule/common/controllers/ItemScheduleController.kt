package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.Week
import com.shiryaev.domain.utils.UtilsConvert
import com.shiryaev.domain.utils.sortWeeks
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.views.CustomItemSchedule
import com.shiryaev.schedule.ui.views.CustomTimeLine
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemScheduleController(
        private val listWeek: List<Week>,
        private val screen: String,
        private val onClickListener: (Schedule) -> Unit
) : BindableItemController<ArrayList<Schedule>, ItemScheduleController.Holder>() {

    var onLongClickListener: ((Schedule) -> Unit)? = null

    private var countItem: Int = 0

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: ArrayList<Schedule>) = data.hashCode().toString()

    fun setCountItem(count: Int) {
        this.countItem = count
    }

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ArrayList<Schedule>>(parent, R.layout.item_schedule) {

        private val timeContainer = itemView.findViewById<CustomTimeLine>(R.id.item_time_container)
        private val container = itemView.findViewById<LinearLayoutCompat>(R.id.item_container)
        private val timeTv = itemView.findViewById<AppCompatTextView>(R.id.item_time_tv)

        override fun bind(data: ArrayList<Schedule>) {
            timeTv.text = UtilsConvert.convertTimeIntToString(data.first().mTimeStart)

            when {
                countItem == 1 -> {
                    timeContainer.apply {
                        setLineTopIsVisible(false)
                        setLineBottomIsVisible(false)
                    }
                }
                adapterPosition == 0 -> {
                    timeContainer.apply {
                        setLineTopIsVisible(false)
                        setLineBottomIsVisible(true)
                    }
                }
                adapterPosition == countItem - 1 -> {
                    timeContainer.apply {
                        setLineTopIsVisible(true)
                        setLineBottomIsVisible(false)
                    }
                }
                else -> {
                    timeContainer.apply {
                        setLineTopIsVisible(true)
                        setLineBottomIsVisible(true)
                    }
                }
            }

            val newListSchedule = if (data.size == 1 && data.first().mWeek == "") {
                data
            } else {
                sortWeeks(data, listWeek)
            }

            with(container) {
                removeAllViews()
                for (item in newListSchedule) {
                    addView(CustomItemSchedule(context, listWeek, screen).apply {
                        onClickListener = { schedule -> this@ItemScheduleController.onClickListener.invoke(schedule) }
                        onLongClickListener = { schedule -> this@ItemScheduleController.onLongClickListener?.invoke(schedule) }
                        setItemSchedule(item)
                    })
                }
            }
        }
    }
}