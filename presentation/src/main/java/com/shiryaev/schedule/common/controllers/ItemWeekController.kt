package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.shiryaev.domain.models.Week
import com.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemWeekController : BindableItemController<Week, ItemWeekController.Holder>() {

    var onClickLayout: ((Week) -> Unit)? = null
    var onLongClickLayout: ((Week) -> Unit)? = null
    var onCLickIndicatorBtn: (() -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Week) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Week>(parent, R.layout.item_week) {

        private val mLayout: MaterialCardView = itemView.findViewById(R.id.item_layout)
        private val mNameWeek: MaterialTextView = itemView.findViewById(R.id.item_tv)
        private val mIndicatorWeek: AppCompatImageButton = itemView.findViewById(R.id.item_ib)

        override fun bind(data: Week) {
            mNameWeek.text = data.mName

            mLayout.apply {
                setOnClickListener {
                    onClickLayout?.invoke(data)
                }
                setOnLongClickListener {
                    onLongClickLayout?.invoke(data)
                    true
                }
            }

            mIndicatorWeek.setOnClickListener {
                Toast.makeText(itemView.context, "Click on Indicator button", Toast.LENGTH_SHORT).show()
            }
        }
    }
}