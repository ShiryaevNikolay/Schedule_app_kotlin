package com.shiryaev.schedule.common

import androidx.databinding.BindingAdapter
import studio.carbonylgroup.textfieldboxes.ExtendedEditText

object CustomBindingAdapter {

    @BindingAdapter("bind:textFiled")
    fun setTextFiled(filed: ExtendedEditText, text: String) {
        filed.setText(text)
    }
}