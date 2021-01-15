package com.shiryaev.schedule.ui.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.shiryaev.schedule.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}