package ru.shiryaev.schedule.ui

import android.content.res.TypedArray
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import ru.shiryaev.schedule.R
import ru.shiryaev.schedule.databinding.ActivityAddNoteBinding
import ru.shiryaev.data.common.CustomFactory
import ru.shiryaev.data.utils.UtilsChecks
import ru.shiryaev.data.viewmodels.AddNoteViewModel
import ru.shiryaev.domain.models.Note
import ru.shiryaev.domain.models.Week
import ru.shiryaev.domain.utils.*
import ru.shiryaev.schedule.common.CallDialogs
import ru.shiryaev.schedule.common.navigation.ActivityClass
import ru.shiryaev.schedule.ui.dialogs.ColorPickerDialog
import ru.shiryaev.schedule.ui.dialogs.ListDialog
import ru.shiryaev.schedule.ui.dialogs.OnClickButtonDialogListener
import ru.shiryaev.schedule.utils.UtilsListData
import studio.carbonylgroup.textfieldboxes.ExtendedEditText

class AddNoteActivity : AppCompatActivity(), View.OnClickListener, OnClickButtonDialogListener {

    private var mNote = Note()
    private var mListLessons: List<String> = listOf()

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
            when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                ActivityClass.EDIT_NOTE.name -> mNote = intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getSerializable(UtilsTable.NOTE) as Note
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
            colorBtn.setOnClickListener(this@AddNoteActivity)
            fab.setOnClickListener(this@AddNoteActivity)
        }

        // Получаем списки для каждого поля из viewModel
        with(mViewModel) {
            // Получаем список занятий
            getListLessons().observe(this@AddNoteActivity) { listLessons ->
                mListLessons = setVisibleBtn(binding.titleListBtn, listLessons)
            }
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
            R.id.color_btn -> {
                showColorPickerDialog()
            }
            R.id.fab -> {
                when(intent.getBundleExtra(UtilsKeys.BUNDLE.name)?.getString(UtilsKeys.REQUEST_CODE.name)) {
                    ActivityClass.CREATE_NOTE.name -> mViewModel.insertNote(mNote)
                    ActivityClass.EDIT_NOTE.name -> mViewModel.updateNote(mNote)
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

    override fun onClick(text: String, oldText: String, week: Week?, dialog: String) {
        when (dialog) {
            UtilsKeys.COLOR_PICK_DIALOG.name -> {
                mNote.mColor = text
                setColor()
            }
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
        setColor()
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

    private fun showColorPickerDialog() {
        ColorPickerDialog()
                .setHeader(resources.getString(R.string.choose_color))
                .setButton(listOf(
                        resources.getString(R.string.dumping),
                        resources.getStringArray(R.array.button_dialog).first(),
                        resources.getStringArray(R.array.button_dialog).last()
                ))
                .setData()
                .show(supportFragmentManager, UtilsKeys.COLOR_PICK_DIALOG.name)
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

    private fun setColor() {
        binding.colorBtn.background.apply {
            if (mNote.mColor == "") {
                val typedValue = TypedValue()
                val typedArray: TypedArray = obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorSurface))
                val color = typedArray.getColor(0, 0)
                typedArray.recycle()
                setTint(color)
            } else {
                setTint(resources.getIntArray(R.array.color_pick)[mNote.mColor.toInt()])
            }
        }
    }
}