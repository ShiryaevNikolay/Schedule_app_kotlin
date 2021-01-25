package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import android.widget.TableRow
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.views.CustomButtonDialog
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemButtonDialogController(
        private val onClickListener: (String) -> Unit
) : BindableItemController<List<String>, ItemButtonDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: List<String>) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<List<String>>(parent, R.layout.custom_layout_button_dialog) {

        private val mLayoutBtn: TableRow = itemView.findViewById(R.id.layout_button)
        private var mData = listOf<String>()

        override fun bind(data: List<String>) {
            mData = data
            data.forEach { textBtn ->
                mLayoutBtn.addView(
                        CustomButtonDialog(itemView.context)
                                .setText(textBtn) { text -> onClickListener.invoke(text) }
                )
            }
        }
    }
}