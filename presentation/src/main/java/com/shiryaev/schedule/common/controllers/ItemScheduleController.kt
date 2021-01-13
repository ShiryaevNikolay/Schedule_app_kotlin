package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import com.shiryaev.domain.models.Schedule
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.views.CustomItemSchedule
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemScheduleController(
        val onClickListener: (Schedule) -> Unit
) : BindableItemController<ArrayList<Schedule>, ItemScheduleController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: ArrayList<Schedule>) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<ArrayList<Schedule>>(parent, R.layout.item_schedule) {

        private val container = itemView.findViewById<LinearLayoutCompat>(R.id.item_container)

        override fun bind(data: ArrayList<Schedule>) {
            with(container) {
                removeAllViews()
                for (item in data) {
                    addView(CustomItemSchedule(context).apply {
                        onClickListener = { schedule -> this@ItemScheduleController.onClickListener.invoke(schedule) }
                        setItemSchedule(item)
                    })
                }
            }
        }
    }
}