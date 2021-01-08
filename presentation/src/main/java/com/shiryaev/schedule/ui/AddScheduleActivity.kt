package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.schedule.common.CallDialogs
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.AddScheduleViewModel
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.domain.utils.UtilsConvert
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import kotlinx.android.synthetic.main.activity_add_schedule.view.*
import java.util.ArrayList

class AddScheduleActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentDay = 0
    private var mWeek = 0

    private var mListTimes: ArrayList<TimeAndWeek> = ArrayList()

    private lateinit var binding: ActivityAddScheduleBinding

    private lateinit var mViewModel: AddScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mCurrentDay = savedInstanceState?.getInt(UtilsKeys.POSITION_PAGE.key)
            ?: intent.getIntExtra(UtilsKeys.POSITION_PAGE.key, 0)

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
                binding.timeBtn.text = UtilsConvert.convertTimeIntToString(hour, minute)
            }
            }
        }
    }

    private fun finishActivity() { finish() }
}