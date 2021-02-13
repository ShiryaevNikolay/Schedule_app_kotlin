package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.WeekSettingsViewModel
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.dialogs.ListDialog
import com.shiryaev.schedule.ui.dialogs.RadioDialog
import com.shiryaev.schedule.utils.UtilsListData

class GeneralSettingsFragment : PreferenceFragmentCompat() {

    private var mSetThemeMode: ((Int) -> Unit)? = null
    private var mSetCurrentWeek: ((Int) -> Unit)? = null
    private var mContext: Context? = null
    private var mListWeeks = mutableListOf<String>()

    private lateinit var mListThemeMode: Array<String>
    private lateinit var mThemeMode: Preference
    private lateinit var mWeeks: Preference
    private lateinit var mCurrentWeeks: Preference
    private lateinit var mNavController: NavController
    private lateinit var mViewModel: WeekSettingsViewModel
    private lateinit var mSharedPref: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mListThemeMode = context.resources.getStringArray(R.array.theme_mode_entries)
        mViewModel = ViewModelProvider(this, CustomFactory(WeekSettingsViewModel())).get(WeekSettingsViewModel::class.java)
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        mSharedPref = PreferenceManager.getDefaultSharedPreferences(mContext)

        mNavController = NavHostFragment.findNavController(this)

        initItems()

        mViewModel.getCountWeek().observe(this) { countWeek ->
            mWeeks.summary = if(countWeek > 0) countWeek.toString() else ""
            mCurrentWeeks.isVisible = countWeek != 0
        }

        mViewModel.getWeeks().observe(this) { weeks ->
            weeks.forEach { week ->
                mListWeeks.add(week.mName)
            }
            saveCurrentWeek()
        }

        mSetThemeMode = { value ->
            mSharedPref.edit()
                .putString(mContext?.resources?.getString(R.string.theme_key), mListThemeMode[value])
                .apply()
            saveThemeMode()
            setThemeMode()
        }

        mSetCurrentWeek = { value ->
            mSharedPref.edit()
                    .putString(mContext?.resources?.getString(R.string.current_week_key), mListWeeks[value])
                    .apply()
            saveCurrentWeek()
        }

        mThemeMode.setOnPreferenceClickListener {
            RadioDialog()
                .setHeader(mContext?.resources?.getString(R.string.theme_mode)!!)
                .setData(UtilsListData.getRadioListDialog(mListThemeMode, getThemeMode()!!)) { position ->
                    mSetThemeMode?.invoke(position)
                }
                .show(childFragmentManager, null)
            true
        }

        mCurrentWeeks.setOnPreferenceClickListener {
            ListDialog()
                    .setData(UtilsListData.getListDialog(mListWeeks)) { position ->
                        mSetCurrentWeek?.invoke(position)
                    }
                    .show(childFragmentManager, null)
            true
        }

        mWeeks.setOnPreferenceClickListener {
            mNavController.navigate(R.id.action_generalSettings_to_weeksSettings)
            true
        }
    }

    private fun initItems() {
        mThemeMode = findPreference(mContext?.resources?.getString(R.string.theme_key)!!)!!
        mWeeks = findPreference(mContext?.resources?.getString(R.string.weeks_key)!!)!!
        mCurrentWeeks = findPreference(mContext?.resources?.getString(R.string.current_week_key)!!)!!
        saveThemeMode()
        saveCurrentWeek()
    }

    private fun saveThemeMode() {
        mThemeMode.summary = getThemeMode()
    }

    private fun saveCurrentWeek() {
        mCurrentWeeks.summary = getCurrentWeek()
    }

    private fun setThemeMode() {
        when(mSharedPref.getString(mContext?.resources?.getString(R.string.theme_key), mListThemeMode.first())) {
            mListThemeMode.first() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) }
            mListThemeMode[1] -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) }
            mListThemeMode.last() -> { AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) }
        }
    }

    private fun getThemeMode() = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext?.resources?.getString(R.string.theme_key), mListThemeMode.first())

    private fun getCurrentWeek() = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext?.resources?.getString(R.string.current_week_key), if(mListWeeks.isEmpty()) "" else mListWeeks.first())
}