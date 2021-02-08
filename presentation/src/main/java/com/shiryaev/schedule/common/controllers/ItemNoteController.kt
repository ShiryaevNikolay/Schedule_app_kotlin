package com.shiryaev.schedule.common.controllers

import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.ViewGroup
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.shiryaev.domain.models.Note
import com.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemNoteController : BindableItemController<Note, ItemNoteController.Holder>() {

    var onClickNote: ((Note) -> Unit)? = null
    var onLongClickNote: ((Note) -> Unit)? = null

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Note) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Note>(parent, R.layout.item_note) {

        private val mNoteCard: MaterialCardView = itemView.findViewById(R.id.note_card)
        private val mNoteContainer: LinearLayoutCompat = itemView.findViewById(R.id.card_container)
        private val mTitle: MaterialTextView = itemView.findViewById(R.id.title_tv)
        private val mText: MaterialTextView = itemView.findViewById(R.id.text_tv)
        private val mDeadline: MaterialTextView = itemView.findViewById(R.id.deadline_tv)

        override fun bind(data: Note) {
            if (data.mTitle != null || data.mTitle != "") {
                mTitle.text = data.mTitle
            }
            mTitle.isVisible = mTitle.text != ""

            mText.text = data.mText

            if (data.mDeadline != null || data.mDeadline != "") {
                mDeadline.text = data.mDeadline
            }
            mDeadline.isVisible = mDeadline.text != ""

            with (mNoteCard) {
                // Меняем цвет заметки
                setColor(mNoteContainer, data)

                // Слушатели нажатий
                setOnClickListener { onClickNote?.invoke(data) }
                setOnLongClickListener {
                    onLongClickNote?.invoke(data)
                    true
                }
            }
        }

        private fun setColor(container: LinearLayoutCompat, note: Note) {
            if (note.mColor == "") {
//                val typedValue = TypedValue()
//                val typedArray: TypedArray = itemView.context.obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorSurface))
//                val color = typedArray.getColor(0, 0)
//                typedArray.recycle()
                container.setBackgroundColor(Color.TRANSPARENT)
//                card.setCardBackgroundColor(color)
            } else {
//                card.setCardBackgroundColor(itemView.context.resources.getIntArray(R.array.color_pick)[note.mColor.toInt()])
                val gd = GradientDrawable().apply {
                    colors = intArrayOf(
                            itemView.context.resources.getIntArray(R.array.color_gradient)[note.mColor.toInt()],
                            itemView.context.resources.getIntArray(R.array.color_pick)[note.mColor.toInt()]
                    )
                    gradientType = GradientDrawable.LINEAR_GRADIENT
                    orientation = GradientDrawable.Orientation.BL_TR
                    shape = GradientDrawable.RECTANGLE
                }
                container.background = gd
//                card.setBackgroundDrawable(gd)
            }
        }
    }
}