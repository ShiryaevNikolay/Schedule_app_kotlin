package com.shiryaev.schedule.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.utils.UtilsChecks
import com.shiryaev.data.viewmodels.AddNoteViewModel
import com.shiryaev.domain.models.Note
import com.shiryaev.domain.utils.*
import com.shiryaev.schedule.R
import com.shiryaev.schedule.common.CallDialogs
import com.shiryaev.schedule.databinding.ActivityAddNoteBinding
import com.shiryaev.schedule.ui.dialogs.ListDialog
import com.shiryaev.schedule.utils.UtilsListData
import studio.carbonylgroup.textfieldboxes.ExtendedEditText

class AddNoteActivity : AppCompatActivity(), View.OnClickListener {

    private var mNote = Note()
    private var mListLessons: List<String> = listOf()
    private var mListDeadline: List<String> = listOf()

    private lateinit var binding: ActivityAddNoteBinding
    private lateinit var mViewModel: AddNoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем тему
        setTheme()

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mViewModel = ViewModelProvider(this, CustomFactory(AddNoteViewModel())).get(AddNoteViewModel::class.java)

        // Получение данных при краше активити или с intent
        if (savedInstanceState != null) { getData(savedInstanceState) }
        else{
            when(intent.getStringExtra(UtilsKeys.REQUEST_CODE.name)) {
                UtilsIntent.EDIT_NOTE.name -> mNote = intent.getSerializableExtra(UtilsTable.NOTE) as Note
            }
        }

        initToolbar()

        setDataToView()

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@AddNoteActivity
            note = mNote
        }

        // Проверяем ввод полей
        with(binding) {
            titleField.setSimpleTextChangeWatcher { theNewText, isError ->
                mNote.mTitle = theNewText
            }
            noteField.setSimpleTextChangeWatcher { theNewText, isError ->
                mNote.mText = theNewText
                mViewModel.setFabIsVisible(UtilsChecks.checkAddNote(mNote.mText))
            }
        }

        // Устанавливаем случашели на кнопки
        with(binding) {
            titleListBtn.setOnClickListener(this@AddNoteActivity)
            deadlineBtn.setOnClickListener(this@AddNoteActivity)
            deadlineListBtn.setOnClickListener(this@AddNoteActivity)
            colorBtn.setOnClickListener(this@AddNoteActivity)
            fab.setOnClickListener(this@AddNoteActivity)
        }

        // Получаем списки для каждого поля из viewModel
        with(mViewModel) {
            // Получаем список занятий
            getListLessons().observe(this@AddNoteActivity, { listLessons ->
                mListLessons = setVisibleBtn(binding.titleListBtn, listLessons)
            })
            // Получаем список deadline
            getDeadline().observe(this@AddNoteActivity, { deadline ->
                mListDeadline = setVisibleBtn(binding.deadlineListBtn, deadline)
            })
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.title_list_btn -> {
                ListDialog()
                        .setData(UtilsListData.getListDialog(mListLessons)) { positionItem ->
                            mNote.mTitle = mListLessons[positionItem]
                            setFieldText(binding.titleEditText, mNote.mTitle ?: "")
                        }
                        .show(supportFragmentManager, null)
            }
            R.id.deadline_btn -> {
                CallDialogs.callDatePickerDialog(this) { date ->
                    mNote.mDeadline = date
                    setSelectedDate()
                }
            }
            R.id.deadline_list_btn -> {
                // TODO
            }
            R.id.color_btn -> {
                // TODO
            }
            R.id.fab -> {
                when(intent.getStringExtra(UtilsKeys.REQUEST_CODE.name)) {
                    UtilsIntent.CREATE_NOTE.name -> mViewModel.insertNote(mNote)
                    UtilsIntent.EDIT_NOTE.name -> mViewModel.updateNote(mNote)
                }
                finishActivity()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState) {
            putSerializable(UtilsTable.NOTE, mNote)
        }
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            setNavigationOnClickListener { finishActivity() }
        }
    }

    private fun getData(savedInstanceState: Bundle) {
        with (savedInstanceState) {
            mNote = getSerializable(UtilsTable.NOTE) as Note
        }
    }

    private fun setDataToView() {
        if (mNote.mDeadline != null || mNote.mDeadline != "") {
            binding.deadlineBtn.text = mNote.mDeadline
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

    private fun setSelectedDate() {
        binding.deadlineBtn.text = mNote.mDeadline
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