package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.shiryaev.domain.models.Note
import com.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemNoteController : BindableItemController<Note, ItemNoteController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: Note) = data.mId.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<Note>(parent, R.layout.item_note) {

        private val mNoteCard: MaterialCardView = itemView.findViewById(R.id.note_card)
        private val mTitle: MaterialTextView = itemView.findViewById(R.id.title_tv)
        private val mText: MaterialTextView = itemView.findViewById(R.id.text_tv)

        override fun bind(data: Note) {
            if (data.mTitle != null) {
                mTitle.text = data.mTitle
            }
            mTitle.isVisible = data.mTitle != null

            mText.text = data.mText

            mNoteCard.setOnClickListener {

            }
        }
    }
}