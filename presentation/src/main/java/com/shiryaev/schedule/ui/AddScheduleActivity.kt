package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.schedule.common.CallDialogs
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.utils.UtilsChecks
import com.shiryaev.data.viewmodels.AddScheduleViewModel
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.domain.utils.UtilsConvert
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.domain.utils.UtilsTableSchedule
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import kotlinx.android.synthetic.main.activity_add_schedule.view.*
import java.util.ArrayList

class AddScheduleActivity : AppCompatActivity(), View.OnClickListener {

    private var mSchedule = Schedule()

    private var mListTimeAndWeek: ArrayList<TimeAndWeek> = ArrayList()
    private var mListTime = ArrayList<Int>()

    private lateinit var binding: ActivityAddScheduleBinding

    private lateinit var mViewModel: AddScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получение данных при краше активити
        if (savedInstanceState != null) {
            getData(savedInstanceState)
            setDataToView()
        } else {
            mSchedule.mDay = intent.getIntExtra(UtilsKeys.POSITION_PAGE.key, 0)
        }

        initToolbar()

        mViewModel = ViewModelProvider(this, CustomFactory(AddScheduleViewModel())).get(AddScheduleViewModel::class.java)

        // Синхронизируем xml с viewModel
        binding.apply {
            vm = mViewModel
            lifecycleOwner = this@AddScheduleActivity
        }

        // Проверяем ввод полей
        with(binding) {
            lessonField.setSimpleTextChangeWatcher { theNewText, isError ->
                mSchedule.mLesson = theNewText
                mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mSchedule.mLesson, mSchedule.mTimeStart))
            }
            teacherField.setSimpleTextChangeWatcher { theNewText, isError ->
                mSchedule.mTeacher = theNewText
            }
            auditField.setSimpleTextChangeWatcher { theNewText, isError ->
                mSchedule.mAudit = theNewText
            }
            examField.setSimpleTextChangeWatcher { theNewText, isError ->
                mSchedule.mExam = theNewText
            }
        }

        // Устанавливаем случашели на кнопки
        with(binding) {
            timeBtn.setOnClickListener(this@AddScheduleActivity)
            fab.setOnClickListener(this@AddScheduleActivity)
        }

        // Получаем список времени
        mViewModel.getTimeStartByDay(mSchedule.mDay).observe(this, { listTimes ->
            mListTimeAndWeek = ArrayList(listTimes)
        })

        mViewModel.getListTimeStart().observe(this, { listTime ->
            binding.timeListBtn.isVisible = listTime.isNotEmpty()
            mListTime = ArrayList(listTime)
        })
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.time_btn -> { CallDialogs.callTimePicker(this@AddScheduleActivity, mSchedule.mWeek, mListTimeAndWeek) { hour, minute ->
                    mSchedule.mTimeStart = ("$hour" + UtilsConvert.convertToCorrectTime(minute)).toInt()
                    binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
                    mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mSchedule.mLesson, mSchedule.mTimeStart))
                }
            }
            R.id.fab -> {
                mViewModel.insertSchedule(mSchedule)
                finishActivity()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putSerializable(UtilsTableSchedule.SCHEDULE, mSchedule)
        }
    }

    private fun getData(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            mSchedule = getSerializable(UtilsTableSchedule.SCHEDULE) as Schedule
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            subtitle = this.resources.getString(R.string.create_a_schedule)
            title = this.resources.getStringArray(R.array.full_days_of_week)[mSchedule.mDay]
            setNavigationOnClickListener { finishActivity() }
        }
    }

    private fun setDataToView() {
        if (mSchedule.mTimeStart != UtilsChecks.TIME_DISABLE) {
            binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
        }
    }

    private fun finishActivity() { finish() }
}