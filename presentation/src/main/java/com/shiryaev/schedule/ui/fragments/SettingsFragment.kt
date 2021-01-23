package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.dialogs.RadioDialog
import com.shiryaev.schedule.utils.UtilsListData

class SettingsFragment : PreferenceFragmentCompat() {

    private var mSetThemeMode: ((Int) -> Unit)? = null
    private var mContext: Context? = null
    private lateinit var mListThemeMode: Array<String>
    private lateinit var mThemeMode: Preference

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mListThemeMode = context.resources.getStringArray(R.array.theme_mode_entries)
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        initItems()

        mSetThemeMode = { value ->
            // TODO
        }

        mThemeMode.setOnPreferenceClickListener {
            RadioDialog()
                .setData(
                    UtilsListData.getRadioListDialog(mListThemeMode, mListThemeMode[0]),
                    mContext?.resources?.getString(R.string.theme_mode)!!)
                { position ->
                    mSetThemeMode?.invoke(position)
                }
                .show(childFragmentManager, null)
            true
        }
    }

    private fun initItems() {
        mThemeMode = findPreference(mContext?.resources?.getString(R.string.theme_key)!!)!!
    }
}