package com.shiryaev.schedule.ui.dialogs

import com.shiryaev.data.common.models.ItemDialog
import com.shiryaev.schedule.common.controllers.ItemHeaderDialogController
import com.shiryaev.schedule.common.controllers.ItemRadioDialogController
import ru.surfstudio.android.easyadapter.ItemList

class RadioDialog : CustomDialog() {

    inline fun setData(listItem: List<ItemDialog>, header: String? = null, crossinline onCLickItemDialog: (positionItemDialog: Int) -> Unit): RadioDialog {
        val itemRadio = ItemRadioDialogController { positionItem ->
            onCLickItemDialog.invoke(if(header != null) positionItem - 1 else positionItem)
            dismiss()
        }
        val mDialogList = ItemList.create().apply {
            addIf(header != null, header, ItemHeaderDialogController())
            addAll(listItem, itemRadio)
        }
        setListToAdapter(mDialogList)
        return this
    }
}