package com.shiryaev.data.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.database.Repository
import com.shiryaev.data.viewmodels.ScheduleViewModel
import javax.inject.Inject

class CustomFactory : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleViewModel() as T
    }
}