package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import com.shiryaev.domain.model.Schedule
import com.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemScheduleController(
        val onClickListener: () -> Unit
) : BindableItemController<Schedule, ItemScheduleController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Schedule) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Schedule>(parent, R.layout.item_schedule) {
        override fun bind(data: Schedule?) {

        }
    }
}