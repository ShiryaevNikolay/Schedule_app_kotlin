package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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
import java.io.Serializable
import java.util.ArrayList

class AddScheduleActivity : AppCompatActivity(), View.OnClickListener {

//    private var mCurrentDay = 0
//    private var mLesson = ""
//    private var mTeacher = ""
//    private var mAudit = ""
//    private var mTypeOfCertification = ""
//    private var mWeek = 0
//    private var mTimeStart = UtilsChecks.TIME_DISABLE
    private var mSchedule = Schedule()

    private var mListTimes: ArrayList<TimeAndWeek> = ArrayList()

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
//            mCurrentDay = intent.getIntExtra(UtilsKeys.POSITION_PAGE.key, 0)
            mSchedule.mDay = intent.getIntExtra(UtilsKeys.POSITION_PAGE.key, 0)
        }

        initToolbar()

        mViewModel = ViewModelProvider(this, CustomFactory(AddScheduleViewModel())).get(AddScheduleViewModel::class.java)

        binding.apply {
            vm = mViewModel
            lifecycleOwner = this@AddScheduleActivity
        }

        // Получаем список времени
        mViewModel.getTimeStartByDay(mSchedule.mDay).observe(this, { listTimes ->
            mListTimes = ArrayList(listTimes)
        })

        binding.lessonField.setSimpleTextChangeWatcher { theNewText, isError ->
//            mLesson = theNewText
//            mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mLesson, mTimeStart))
            mSchedule.mLesson = theNewText
            mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mSchedule.mLesson, mSchedule.mTimeStart))
        }

        binding.timeBtn.setOnClickListener(this)

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

    override fun onClick(v: View) {
        when(v.id) {
            R.id.time_btn -> { CallDialogs.callTimePicker(this@AddScheduleActivity, mSchedule.mWeek, mListTimes) { hour, minute ->
                    mSchedule.mTimeStart = ("$hour" + UtilsConvert.convertToCorrectTime(minute)).toInt()
                    binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
                    mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mSchedule.mLesson, mSchedule.mTimeStart))
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
//            putInt(UtilsTableSchedule.SCHEDULE_COLUMN_DAY, mCurrentDay)
//            putString(UtilsTableSchedule.SCHEDULE_COLUMN_LESSON, mLesson)
//            putString(UtilsTableSchedule.SCHEDULE_COLUMN_TEACHER, mTeacher)
//            putString(UtilsTableSchedule.SCHEDULE_COLUMN_AUDIT, mAudit)
//            putString(UtilsTableSchedule.SCHEDULE_COLUMN_EXAM, mTypeOfCertification)
//            putInt(UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART, mTimeStart)

            putSerializable(UtilsTableSchedule.SCHEDULE, mSchedule)
        }
    }

    private fun getData(savedInstanceState: Bundle) {
        with(savedInstanceState) {
//            mSchedule.mDay = getInt(UtilsTableSchedule.SCHEDULE_COLUMN_DAY)
//            mSchedule.mTimeStart = getInt(UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART)
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