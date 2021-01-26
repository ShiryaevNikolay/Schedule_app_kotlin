package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import com.google.android.material.textview.MaterialTextView
import com.shiryaev.domain.models.Week
import com.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemWeekController : BindableItemController<Week, ItemWeekController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Week) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Week>(parent, R.layout.item_week) {

        private val mNameWeek: MaterialTextView = itemView.findViewById(R.id.item_tv)

        override fun bind(data: Week) {
            mNameWeek.text = data.mName
        }
    }
}