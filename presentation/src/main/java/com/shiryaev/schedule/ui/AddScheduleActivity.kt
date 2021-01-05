package com.shiryaev.schedule.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.shiryaev.data.AppDelegate
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.database.Repository
import com.shiryaev.data.viewmodels.ScheduleViewModel
import com.shiryaev.schedule.databinding.ActivityAddScheduleBinding
import javax.inject.Inject

class AddScheduleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddScheduleBinding

    private lateinit var mScheduleViewModel: ScheduleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = CustomFactory()
        mScheduleViewModel = ViewModelProvider(this, factory).get(ScheduleViewModel::class.java)

        binding.lifecycleOwner = this

        binding.toolbar.setNavigationOnClickListener { finishActivity() }
    }

    private fun finishActivity() { finish() }
}