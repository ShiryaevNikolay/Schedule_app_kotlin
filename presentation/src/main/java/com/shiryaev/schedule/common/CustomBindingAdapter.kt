package com.shiryaev.schedule.common

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.surfstudio.android.easyadapter.EasyAdapter
import studio.carbonylgroup.textfieldboxes.ExtendedEditText

object CustomBindingAdapter {

    @BindingAdapter("bind:textFiled")
    fun setTextFiled(filed: ExtendedEditText, text: String) {
        filed.setText(text)
    }

    @BindingAdapter("bind:data")
    fun configureRecyclerViewDialog(recyclerView: RecyclerView, listItems: List<String>) {
        val easyAdapter = EasyAdapter()
        with(recyclerView) {
            adapter = easyAdapter

        }
    }
}