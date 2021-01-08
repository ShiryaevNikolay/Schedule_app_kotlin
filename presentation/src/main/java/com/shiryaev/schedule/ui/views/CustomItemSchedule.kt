package com.shiryaev.schedule.ui.views

import android.content.Context
import android.widget.FrameLayout
import com.shiryaev.domain.models.Schedule
import com.shiryaev.schedule.R
import kotlinx.android.synthetic.main.custom_card_schedule.view.*

abstract class CustomItemSchedule(context: Context) : FrameLayout(context) {

    var onClickListener: ((Schedule) -> Unit)? = null
    private var itemSchedule: Schedule? = null

    init {
        inflate(context, R.layout.custom_card_schedule, this)

        this.item_card.setOnClickListener { itemSchedule?.let { it1 -> onClickListener?.invoke(it1) } }
    }

    fun setItemSchedule(item: Schedule) {
        this.itemSchedule = item
    }
}