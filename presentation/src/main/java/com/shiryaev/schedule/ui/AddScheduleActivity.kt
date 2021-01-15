package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.schedule.common.CallDialogs
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.utils.UtilsChecks
import com.shiryaev.data.viewmodels.AddScheduleViewModel
import com.shiryaev.domain.models.Schedule
import com.shiryaev.domain.models.TimeAndWeek
import com.shiryaev.domain.utils.*
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import com.shiryaev.schedule.ui.dialogs.ListDialog
import com.shiryaev.schedule.utils.UtilsListData
import kotlinx.android.synthetic.main.activity_add_schedule.view.*
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import java.util.ArrayList

class AddScheduleActivity : AppCompatActivity(), View.OnClickListener {

    private var mSchedule = Schedule()

    private var mListTimeAndWeek: ArrayList<TimeAndWeek> = ArrayList()
    private var mListLessons: List<String> = listOf()
    private var mListTeachers: List<String> = listOf()
    private var mListAudits: List<String> = listOf()
    private var mListExams: List<String> = listOf()
    private var mListTime: List<Int> = listOf()

    private lateinit var binding: ActivityAddScheduleBinding

    private lateinit var mViewModel: AddScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this, CustomFactory(AddScheduleViewModel())).get(AddScheduleViewModel::class.java)

        // Получение данных при краше активити или с intent
        if (savedInstanceState != null) { getData(savedInstanceState) }
        else {
            when(intent.getStringExtra(UtilsKeys.REQUEST_CODE.name)) {
                UtilsIntent.CREATE_LESSON.name -> mSchedule.mDay = intent.getIntExtra(UtilsKeys.POSITION_PAGE.name,0)
                UtilsIntent.EDIT_LESSON.name -> mSchedule = intent.getSerializableExtra(UtilsTableSchedule.SCHEDULE) as Schedule
            }
        }

        initToolbar()

        setDataToView()

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@AddScheduleActivity
            schedule = mSchedule
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
            lessonListBtn.setOnClickListener(this@AddScheduleActivity)
            teacherListBtn.setOnClickListener(this@AddScheduleActivity)
            auditListBtn.setOnClickListener(this@AddScheduleActivity)
            examListBtn.setOnClickListener(this@AddScheduleActivity)
            timeBtn.setOnClickListener(this@AddScheduleActivity)
            timeListBtn.setOnClickListener(this@AddScheduleActivity)
            fab.setOnClickListener(this@AddScheduleActivity)
        }

        // Получаем списки для каждого поля из viewModel
        with(mViewModel) {
            // Получаем список время+неделя (для текущего дня)
            getTimeStartByDay(mSchedule.mDay).observe(this@AddScheduleActivity, { listTimes ->
                mListTimeAndWeek = ArrayList(listTimes)
            })
            // Получаем список занятий
            getListLessons().observe(this@AddScheduleActivity, { listLessons ->
                mListLessons = setVisibleBtn(binding.lessonListBtn, listLessons)
            })
            // Получаем список занятий
            getListTeachers().observe(this@AddScheduleActivity, { listTeachers ->
                mListTeachers = setVisibleBtn(binding.teacherListBtn, listTeachers)
            })
            // Получаем список занятий
            getListAudits().observe(this@AddScheduleActivity, { listAudits ->
                mListAudits = setVisibleBtn(binding.auditListBtn, listAudits)
            })
            // Получаем список занятий
            getListExams().observe(this@AddScheduleActivity, { listExams ->
                mListExams = setVisibleBtn(binding.examListBtn, listExams)
            })
            // Получаем список времени
            getListTimeStart().observe(this@AddScheduleActivity, { listTime ->
                mListTime = setVisibleBtn(binding.timeListBtn, listTime)
            })
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.time_btn -> { CallDialogs.callTimePicker(this@AddScheduleActivity, mSchedule.mWeek, mListTimeAndWeek) { hour, minute ->
                    mSchedule.mTimeStart = ("$hour" + UtilsConvert.convertToCorrectTime(minute)).toInt()
                    setSelectedTime()
                }
            }
            R.id.lesson_list_btn -> {
                ListDialog { positionItem ->
                    mSchedule.mLesson = mListLessons[positionItem]
                    setFieldText(binding.lessonEditText, mSchedule.mLesson)
                }.apply {
                    setData(UtilsListData.getListDialog(mListLessons))
                }.show(supportFragmentManager, null)
            }
            R.id.teacher_list_btn -> {
                ListDialog { positionItem ->
                    mSchedule.mTeacher = mListTeachers[positionItem]
                    setFieldText(binding.teacherEditText, mSchedule.mTeacher!!)
                }.apply {
                    setData(UtilsListData.getListDialog(mListTeachers))
                }.show(supportFragmentManager, null)
            }
            R.id.audit_list_btn -> {
                ListDialog { positionItem ->
                    mSchedule.mAudit = mListAudits[positionItem]
                    setFieldText(binding.auditEditText, mSchedule.mAudit!!)
                }.apply {
                    setData(UtilsListData.getListDialog(mListAudits))
                }.show(supportFragmentManager, null)
            }
            R.id.exam_list_btn -> {
                ListDialog { positionItem ->
                    mSchedule.mExam = mListExams[positionItem]
                    setFieldText(binding.examEditText, mSchedule.mExam!!)
                }.apply {
                    setData(UtilsListData.getListDialog(mListExams))
                }.show(supportFragmentManager, null)
            }
            R.id.time_list_btn -> {
                ListDialog { positionItem ->
                    mSchedule.mTimeStart = mListTime[positionItem]
                    setSelectedTime()
                }.apply {
                    setData(UtilsListData.getListTimeDialog(mListTime))
                }.show(supportFragmentManager, null)
            }
            R.id.fab -> {
                when(intent.getStringExtra(UtilsKeys.REQUEST_CODE.name)) {
                    UtilsIntent.CREATE_LESSON.name -> mViewModel.insertSchedule(mSchedule)
                    UtilsIntent.EDIT_LESSON.name -> mViewModel.updateSchedule(mSchedule)
                }
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

    private fun initToolbar() {
        with(binding.toolbar) {
            when(intent.getStringExtra(UtilsKeys.REQUEST_CODE.name)) {
                UtilsIntent.CREATE_LESSON.name -> subtitle = this.resources.getString(R.string.create_lesson)
                UtilsIntent.EDIT_LESSON.name -> subtitle = this.resources.getString(R.string.edit_lesson)
            }
            title = this.resources.getStringArray(R.array.full_days_of_week)[mSchedule.mDay]
            setNavigationOnClickListener { finishActivity() }
        }
    }

    private fun getData(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            mSchedule = getSerializable(UtilsTableSchedule.SCHEDULE) as Schedule
        }
    }

    private fun setDataToView() {
        if (mSchedule.mTimeStart != UtilsChecks.TIME_DISABLE) {
            binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
        }
    }

    private fun <T> setVisibleBtn(btn: AppCompatImageButton, newList: List<T>): List<T> {
        val currentList = nonNullValues(newList.distinct())
        btn.isVisible = currentList.isNotEmpty()
        return currentList
    }

    private fun setFieldText(field: ExtendedEditText, text: String) {
        with(field) {
            setText(text)
            setSelection(text.length)
        }
    }

    private fun setSelectedTime() {
        binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
        mViewModel.setFabIsVisible(UtilsChecks.checkAddSchedule(mSchedule.mLesson, mSchedule.mTimeStart))
    }

    private fun finishActivity() { finish() }
}