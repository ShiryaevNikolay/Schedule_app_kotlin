package com.shiryaev.schedule.ui.dialogs

import com.shiryaev.data.common.models.ItemDialog
import com.shiryaev.schedule.common.controllers.ItemListDialogController
import ru.surfstudio.android.easyadapter.ItemList

class ListDialog : CustomDialog() {

    inline fun setData(listItem: List<ItemDialog>, crossinline onClickItemDialog: (positionItemDialog: Int) -> Unit): ListDialog {
        val itemDialog = ItemListDialogController { positionItem ->
            onClickItemDialog.invoke(positionItem)
            dismiss()
        }
        val mDialogList = ItemList.create().apply {
            addAll(listItem, itemDialog)
        }
        setListToAdapter(mDialogList)
        return this
    }
}