package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.schedule.common.CallDialogs
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.AddScheduleViewModel
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.domain.utils.UtilsConvert
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.domain.utils.UtilsTableSchedule
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import kotlinx.android.synthetic.main.activity_add_schedule.view.*
import java.util.ArrayList

class AddScheduleActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentDay = 0
    private var mWeek = 0
    private var mTimeStart = 0

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
        }
        else {
            mCurrentDay = intent.getIntExtra(UtilsKeys.POSITION_PAGE.key, 0)
        }

        initToolbar()

        mViewModel = ViewModelProvider(this, CustomFactory(AddScheduleViewModel())).get(AddScheduleViewModel::class.java)

        binding.apply {
            vm = mViewModel
            lifecycleOwner = this@AddScheduleActivity
            toolbar.setNavigationOnClickListener { finishActivity() }
        }

        // Получаем список времени
        mViewModel.getTimeStartByDay(mCurrentDay).observe(this, { listTimes ->
            mListTimes = ArrayList(listTimes)
        })

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
            R.id.time_btn -> { CallDialogs.callTimePicker(this@AddScheduleActivity, mWeek, mListTimes) { hour, minute ->
                    mTimeStart = ("$hour$minute").toInt()
                    binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mTimeStart)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putInt(UtilsTableSchedule.SCHEDULE_COLUMN_DAY, mCurrentDay)
            putInt(UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART, mTimeStart)
        }
    }

    private fun getData(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            mCurrentDay = getInt(UtilsTableSchedule.SCHEDULE_COLUMN_DAY)
            mTimeStart = getInt(UtilsTableSchedule.SCHEDULE_COLUMN_TIMESTART)
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            subtitle = this.resources.getString(R.string.create_a_schedule)
            title = this.resources.getStringArray(R.array.full_days_of_week)[mCurrentDay]
        }
    }

    private fun setDataToView() {
        Toast.makeText(this, "$mTimeStart", Toast.LENGTH_SHORT).show()
        binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mTimeStart)
    }

    private fun finishActivity() { finish() }
}