package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.database.Repository
import com.shiryaev.data.viewmodels.ScheduleViewModel
import com.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import javax.inject.Inject

class AddScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddScheduleBinding

    private lateinit var mScheduleViewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mScheduleViewModel = ViewModelProvider(this, CustomFactory()).get(ScheduleViewModel::class.java)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater).apply {
            vm = mScheduleViewModel
            lifecycleOwner = this@AddScheduleActivity
        }
        setContentView(binding.root)


        binding.apply {
//            vm = mScheduleViewModel
//            lifecycleOwner = this@AddScheduleActivity
            toolbar.setNavigationOnClickListener { finishActivity() }
            lessonField.setSimpleTextChangeWatcher { theNewText, isError ->
                mScheduleViewModel.setLessonText(theNewText)
            }
        }
    }

    private fun finishActivity() { finish() }
}