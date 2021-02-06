package com.shiryaev.schedule.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.ActivityAddNoteBinding

class AddNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Устанавливаем тему
        setTheme()

        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
    }

    private fun initToolbar() {
        with(binding.toolbar) {
            setNavigationOnClickListener { finishActivity() }
        }
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