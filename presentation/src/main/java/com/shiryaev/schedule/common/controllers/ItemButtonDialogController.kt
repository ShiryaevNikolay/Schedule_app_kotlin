package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import android.widget.TableRow
import android.widget.Toast
import androidx.appcompat.widget.LinearLayoutCompat
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.views.CustomButtonDialog
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder

class ItemButtonDialogController : BindableItemController<List<String>, ItemButtonDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: List<String>) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<List<String>>(parent, R.layout.custom_layout_button_dialog) {

        private val mLayoutBtn: TableRow = itemView.findViewById(R.id.layout_button)

        override fun bind(data: List<String>) {
            data.forEach { textBtn ->
                mLayoutBtn.addView(
                        CustomButtonDialog(itemView.context)
                                .setText(textBtn) { text ->
                                    Toast.makeText(itemView.context, text, Toast.LENGTH_SHORT).show()
                                }
                )
            }
        }
    }
}