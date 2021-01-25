package com.shiryaev.schedule.ui.dialogs

import com.shiryaev.schedule.common.controllers.ItemButtonDialogController
import com.shiryaev.schedule.common.controllers.ItemFieldDialogController
import com.shiryaev.schedule.common.controllers.ItemHeaderDialogController
import ru.surfstudio.android.easyadapter.ItemList

class FieldDialog : CustomDialog() {

    private var mHeader: String? = null
    private var mButton: List<String>? = null

    fun setHeader(header: String): FieldDialog {
        mHeader = header
        return this
    }

    fun setButton(button: List<String>): FieldDialog {
        mButton = button
        return this
    }

    fun setData(textField: String = "", onTextChanged: (String) -> Unit): FieldDialog {
        val itemField = ItemFieldDialogController { text ->
            onTextChanged.invoke(text)
        }
        val mDialogList = ItemList.create().apply {
            addIf(mHeader != null, mHeader, ItemHeaderDialogController())
            addAll(listOf(textField), itemField)
            addAllIf(mButton != null, listOf(mButton), ItemButtonDialogController())
        }
        setListToAdapter(mDialogList)
        return this
    }
}