package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.WeekSettingsViewModel
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.dialogs.RadioDialog
import com.shiryaev.schedule.utils.UtilsListData

class GeneralSettingsFragment : PreferenceFragmentCompat() {

    private var mSetThemeMode: ((Int) -> Unit)? = null
    private var mContext: Context? = null
    private lateinit var mListThemeMode: Array<String>
    private lateinit var mThemeMode: Preference
    private lateinit var mWeeks: Preference
    private lateinit var mNavController: NavController
    private lateinit var mViewModel: WeekSettingsViewModel

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

        mNavController = NavHostFragment.findNavController(this)

        initItems()

        mViewModel.getCountWeek().observe(this) { countWeek ->
            mWeeks.summary = if(countWeek > 0) countWeek.toString() else ""
        }

        mSetThemeMode = { value ->
            PreferenceManager.getDefaultSharedPreferences(mContext).edit()
                .putString(mContext?.resources?.getString(R.string.theme_key), mListThemeMode[value])
                .apply()
            saveThemeMode()
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

        mWeeks.setOnPreferenceClickListener {
            mNavController.navigate(R.id.action_generalSettings_to_weeksSettings)
            true
        }
    }

    private fun initItems() {
        mThemeMode = findPreference(mContext?.resources?.getString(R.string.theme_key)!!)!!
        mWeeks = findPreference(mContext?.resources?.getString(R.string.weeks_key)!!)!!
        saveThemeMode()
    }

    private fun saveThemeMode() {
        mThemeMode.summary = getThemeMode()
    }

    private fun getThemeMode() = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext?.resources?.getString(R.string.theme_key), mListThemeMode[0])
}