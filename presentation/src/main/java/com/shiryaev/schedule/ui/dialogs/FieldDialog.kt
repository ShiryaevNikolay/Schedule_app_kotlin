package com.shiryaev.schedule.ui.dialogs

import android.content.Context
import com.shiryaev.schedule.common.controllers.ItemButtonDialogController
import com.shiryaev.schedule.common.controllers.ItemFieldDialogController
import com.shiryaev.schedule.common.controllers.ItemHeaderDialogController
import ru.surfstudio.android.easyadapter.ItemList

class FieldDialog : CustomDialog() {

    private var mText = ""
    private var mHeader: String? = null
    private var mButton: List<String>? = null
    private lateinit var mItemButtonController: ItemButtonDialogController
    private lateinit var mOnClickListener: OnClickButtonDialogListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mOnClickListener = parentFragment as OnClickButtonDialogListener
    }

    fun setHeader(header: String): FieldDialog {
        mHeader = header
        return this
    }

    fun setButton(button: List<String>): FieldDialog {
        mButton = button
        mItemButtonController = ItemButtonDialogController { textBtn ->
            clickBtn(textBtn)
        }
        return this
    }

    fun setData(nameWeek: String = ""): FieldDialog {
        mText = nameWeek
        val itemField = ItemFieldDialogController { text ->
            mText = text
        }
        val mDialogList = ItemList.create().apply {
            addIf(mHeader != null, mHeader, ItemHeaderDialogController())
            addAll(listOf(nameWeek), itemField)
            addAllIf(mButton != null, listOf(mButton), mItemButtonController)
        }
        setListToAdapter(mDialogList)
        return this
    }

    private fun clickBtn(text: String) {
        when(text) {
            mButton?.first() -> { dismiss() }
            mButton?.last() -> { mOnClickListener.onClick(mText) }
        }
    }
}