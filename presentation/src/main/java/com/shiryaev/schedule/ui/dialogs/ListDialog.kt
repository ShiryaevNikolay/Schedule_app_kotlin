package com.shiryaev.schedule.ui.dialogs

import com.shiryaev.data.common.models.ItemDialog
import ru.surfstudio.android.easyadapter.ItemList

class ListDialog(
        private val onClickItemDialog: (positionItemDialog: Int) -> Unit
) : CustomDialog() {

    private lateinit var mDialogList: ItemList

    fun setData(listItem: List<ItemDialog>) {
        mItemDialogController.onCLickListener = { positionItem ->
            onClickItemDialog.invoke(positionItem)
            dismiss()
        }
        mDialogList = ItemList.create().apply {
            addAll(listItem, mItemDialogController)
        }
        setListToAdapter(mDialogList)
    }
}