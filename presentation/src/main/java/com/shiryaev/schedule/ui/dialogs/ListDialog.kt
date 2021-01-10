package com.shiryaev.schedule.ui.dialogs

import com.shiryaev.data.common.models.ItemDialog

class ListDialog(
        private val onClickItemDialog: (positionItemDialog: Int) -> Unit
) : CustomDialog() {

    fun setData(listItem: List<ItemDialog>) {
        mItemDialogController.onCLickListener = { positionItem ->
            onClickItemDialog.invoke(positionItem)
        }
        setListToAdapter(listItem)
    }
}