package com.shiryaev.data.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeScheduleViewModel : ViewModel() {

    private val mHeightTopBar = MutableLiveData<Int>()

    fun setHeightTopBar(height: Int) {
        mHeightTopBar.value = height
    }

    fun getHeightTopBar() = mHeightTopBar
}