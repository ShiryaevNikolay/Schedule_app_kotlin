package com.shiryaev.schedule.common.controllers

import android.view.ViewGroup
import com.shiryaev.schedule.R
import ru.surfstudio.android.easyadapter.controller.BindableItemController
import ru.surfstudio.android.easyadapter.holder.BindableViewHolder
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import studio.carbonylgroup.textfieldboxes.TextFieldBoxes

class ItemFieldDialogController(
        private val onTextChanged: (String) -> Unit
) : BindableItemController<String, ItemFieldDialogController.Holder>() {

    override fun createViewHolder(parent: ViewGroup) = Holder(parent)

    override fun getItemId(data: String) = data.hashCode().toString()

    inner class Holder(parent: ViewGroup) : BindableViewHolder<String>(parent, R.layout.custom_field_dialog) {

        private val mField: TextFieldBoxes = itemView.findViewById(R.id.field)
        private val mFiledEt: ExtendedEditText = itemView.findViewById(R.id.field_et)

        override fun bind(data: String) {
            mFiledEt.setText(data)
            mField.setSimpleTextChangeWatcher { theNewText, isError ->
                onTextChanged.invoke(theNewText)
            }
        }
    }
}