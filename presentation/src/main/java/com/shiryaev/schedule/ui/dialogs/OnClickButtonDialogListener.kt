package com.shiryaev.schedule.ui.dialogs

import com.shiryaev.domain.models.Week

interface OnClickButtonDialogListener {
    fun onClick(text: String, week: Week?)
}