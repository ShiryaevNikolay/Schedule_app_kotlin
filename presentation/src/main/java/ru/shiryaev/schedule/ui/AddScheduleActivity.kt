package ru.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import ru.shiryaev.domain.models.Schedule
import ru.shiryaev.domain.models.TimeAndWeek
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.*
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import kotlinx.android.synthetic.main.activity_add_schedule.view.*
import ru.shiryaev.data.common.CustomFactory
import ru.shiryaev.data.utils.UtilsChecks
import ru.shiryaev.data.viewmodels.AddScheduleViewModel
import ru.shiryaev.schedule.common.CallDialogs
import ru.shiryaev.schedule.common.navigation.ActivityClass
import ru.shiryaev.schedule.ui.dialogs.ListDialog
import ru.shiryaev.schedule.utils.UtilsListData
import studio.carbonylgroup.textfieldboxes.ExtendedEditText
import java.util.ArrayList

class AddScheduleActivity : AppCompatActivity(), View.OnClickListener {

    private var mSchedule = Schedule()

    private var mListWeek: List<Week> = listOf()
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

        // Устанавливаем тему
        setTheme()

        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this, CustomFactory(AddScheduleViewModel())).get(AddScheduleViewModel::class.java)

        // Получение данных при краше активити или с intent
        if (savedInstanceState != null) { getData(savedInstanceState) }
        else {
            when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                ActivityClass.CREATE_SCHEDULE.name -> mSchedule.mDay = intent.getBundleExtra(UtilsKeys.BUNDLE.name)!!.getInt(UtilsKeys.POSITION_PAGE.name, 0)
                ActivityClass.EDIT_SCHEDULE.name -> mSchedule = intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getSerializable(UtilsTable.SCHEDULE) as Schedule
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
            weekBtn.setOnClickListener(this@AddScheduleActivity)
            fab.setOnClickListener(this@AddScheduleActivity)
        }

        // Получаем списки для каждого поля из viewModel
        with(mViewModel) {
            // Получаем список недель
            getWeeks().observe(this@AddScheduleActivity) { listWeek ->
                mListWeek = listWeek
            }
            // Получаем список время+неделя (для текущего дня)
            getTimeStartByDay(mSchedule.mDay).observe(this@AddScheduleActivity) { listTimes ->
                mListTimeAndWeek = ArrayList(listTimes)
            }
            // Получаем список занятий
            getListLessons().observe(this@AddScheduleActivity) { listLessons ->
                mListLessons = setVisibleBtn(binding.lessonListBtn, listLessons)
            }
            // Получаем список занятий
            getListTeachers().observe(this@AddScheduleActivity) { listTeachers ->
                mListTeachers = setVisibleBtn(binding.teacherListBtn, listTeachers)
            }
            // Получаем список занятий
            getListAudits().observe(this@AddScheduleActivity) { listAudits ->
                mListAudits = setVisibleBtn(binding.auditListBtn, listAudits)
            }
            // Получаем список занятий
            getListExams().observe(this@AddScheduleActivity) { listExams ->
                mListExams = setVisibleBtn(binding.examListBtn, listExams)
            }
            // Получаем список времени
            getListTimeStart().observe(this@AddScheduleActivity) { listTime ->
                mListTime = setVisibleBtn(binding.timeListBtn, listTime)
            }
        }
    }

    override fun onClick(v: View) {
        when(v.id) {
            R.id.time_btn -> {
                CallDialogs.callTimePicker(this@AddScheduleActivity, mSchedule.mWeek, mListTimeAndWeek) { hour, minute ->
                    mSchedule.mTimeStart = ("$hour" + UtilsConvert.convertToCorrectTime(minute)).toInt()
                    setSelectedTime()
                }.show(supportFragmentManager, null)
            }
            R.id.lesson_list_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListDialog(mListLessons)) { positionItem ->
                        mSchedule.mLesson = mListLessons[positionItem]
                        setFieldText(binding.lessonEditText, mSchedule.mLesson)
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.teacher_list_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListDialog(mListTeachers)) { positionItem ->
                        mSchedule.mTeacher = mListTeachers[positionItem]
                        setFieldText(binding.teacherEditText, mSchedule.mTeacher!!)
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.audit_list_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListDialog(mListAudits)) { positionItem ->
                        mSchedule.mAudit = mListAudits[positionItem]
                        setFieldText(binding.auditEditText, mSchedule.mAudit!!)
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.exam_list_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListDialog(mListExams)) { positionItem ->
                        mSchedule.mExam = mListExams[positionItem]
                        setFieldText(binding.examEditText, mSchedule.mExam!!)
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.time_list_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListTimeDialog(mListTime)) { positionItem ->
                        mSchedule.mTimeStart = mListTime[positionItem]
                        setSelectedTime()
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.week_btn -> {
                ListDialog()
                    .setData(UtilsListData.getListNameWeekDialog(mListWeek, this)) { positionItem ->
                        if (positionItem == mListWeek.size) {
                            mSchedule.mWeek = ""
                        } else {
                            mSchedule.mWeek = mListWeek[positionItem].mName
                        }
                        setSelectedWeek()
                    }
                    .show(supportFragmentManager, null)
            }
            R.id.fab -> {
                when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                    ActivityClass.CREATE_SCHEDULE.name -> mViewModel.insertSchedule(mSchedule)
                    ActivityClass.EDIT_SCHEDULE.name -> mViewModel.updateSchedule(mSchedule)
                }
                finishActivity()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putSerializable(UtilsTable.SCHEDULE, mSchedule)
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                ActivityClass.CREATE_SCHEDULE.name -> subtitle = this.resources.getString(R.string.create_lesson)
                ActivityClass.EDIT_SCHEDULE.name -> subtitle = this.resources.getString(R.string.edit_lesson)
            }
            title = this.resources.getStringArray(R.array.full_days_of_week)[mSchedule.mDay]
            setNavigationOnClickListener { finishActivity() }
        }
    }

    private fun getData(savedInstanceState: Bundle) {
        with(savedInstanceState) {
            mSchedule = getSerializable(UtilsTable.SCHEDULE) as Schedule
        }
    }

    private fun setDataToView() {
        if (mSchedule.mTimeStart != UtilsChecks.TIME_DISABLE) {
            binding.timeBtn.text = UtilsConvert.convertTimeIntToString(mSchedule.mTimeStart)
        }
        binding.weekBtn.text = mSchedule.mWeek
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

    private fun setSelectedWeek() {
        binding.weekBtn.text = mSchedule.mWeek
    }

    private fun finishActivity() { finish() }

    private fun setTheme() {
        val listThemeMode = resources.getStringArray(R.array.theme_mode_entries)
        when (PreferenceManager.getDefaultSharedPreferences(this).getString(resources.getString(R.string.theme_key), listThemeMode.first())) {
            listThemeMode.first() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
            listThemeMode[1] -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
            listThemeMode.last() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        }
    }
}