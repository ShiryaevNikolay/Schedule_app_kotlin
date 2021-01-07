package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.ScheduleViewModel
import com.shiryaev.schedule.databinding.ActivityAddScheduleBinding

class AddScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddScheduleBinding

    private lateinit var mScheduleViewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mScheduleViewModel = ViewModelProvider(this, CustomFactory()).get(ScheduleViewModel::class.java)


        binding.apply {
            vm = mScheduleViewModel
            lifecycleOwner = this@AddScheduleActivity
            toolbar.setNavigationOnClickListener { finishActivity() }
            lessonField.setSimpleTextChangeWatcher { theNewText, isError ->
//                mScheduleViewModel.setLessonText(theNewText)
            }
        }

//        binding.fab.setOnClickListener {
//            CustomDialog { schedule, positionItem ->
//
//            }.apply {
//                setData(Schedule(
//                    mLesson = "",
//                    mTeacher = "",
//                    mAudit = "",
//                    mTimeStart = 0,
//                    mTimeEnd = 0,
//                    mWeek = 0,
//                    mDay = 0
//            ), UtilsListData.getListScheduleDialog(this@AddScheduleActivity))
//            }.show(supportFragmentManager, null)
//        }
    }

    private fun finishActivity() { finish() }
}