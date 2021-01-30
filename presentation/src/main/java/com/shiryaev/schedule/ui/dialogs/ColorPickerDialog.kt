package com.shiryaev.schedule.ui.dialogs

import android.content.Context
import com.shiryaev.domain.models.Week
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.common.controllers.ItemButtonDialogController
import com.shiryaev.schedule.common.controllers.ItemHeaderDialogController
import com.shiryaev.schedule.common.controllers.ItemListColorPickController
import ru.surfstudio.android.easyadapter.ItemList

class ColorPickerDialog : CustomDialog() {

    private var mHeader: String? = null
    private var mButton: List<String>? = null
    private lateinit var mWeek: Week
    private lateinit var mItemButtonController: ItemButtonDialogController
    private lateinit var mOnClickListener: OnClickButtonDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnClickListener = parentFragment as OnClickButtonDialogListener
    }

    fun setButton(button: List<String>): ColorPickerDialog {
        mButton = button
        mItemButtonController = ItemButtonDialogController { textBtn ->
            clickBtn(textBtn)
        }
        return this
    }

    fun setHeader(header: String): ColorPickerDialog {
        mHeader = header
        return this
    }

    fun setData(week: Week): ColorPickerDialog {
        mWeek = week
        val colorPick = ItemListColorPickController { color -> mWeek.mColor = color.toString() }
        val mDialogList = ItemList.create().apply {
            addIf(mHeader != null, mHeader, ItemHeaderDialogController())
            addIf(true,  0, colorPick)
            addAllIf(mButton != null, listOf(mButton), mItemButtonController)
        }
        setListToAdapter(mDialogList)
        return this
    }

    private fun clickBtn(text: String) {
        when(text) {
            mButton?.first() -> {
                mWeek.mColor = ""
                mOnClickListener.onClick(week = mWeek, dialog = UtilsKeys.COLOR_PICK_DIALOG.name)
                dismiss() }
            mButton?.get(1) -> { dismiss() }
            mButton?.last() -> {
                mOnClickListener.onClick(week = mWeek, dialog = UtilsKeys.COLOR_PICK_DIALOG.name)
                dismiss()
            }
        }
    }
}