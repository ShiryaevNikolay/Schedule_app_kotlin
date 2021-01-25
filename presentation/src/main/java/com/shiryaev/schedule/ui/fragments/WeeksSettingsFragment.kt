package com.shiryaev.schedule.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shiryaev.schedule.databinding.FrWeeksSettingsBinding
import com.shiryaev.schedule.ui.dialogs.FieldDialog

class WeeksSettingsFragment : Fragment() {

    private var _binding: FrWeeksSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FrWeeksSettingsBinding.inflate(inflater, container, false)

        mNavController = NavHostFragment.findNavController(this)

        binding.toolbar.setNavigationOnClickListener { mNavController.popBackStack() }

        binding.fab.setOnClickListener { showDialog() }

        return binding.root
    }

    private fun showDialog() {
        FieldDialog()
            .setHeader("Введите название недели")
            .setButton(listOf("ОТМЕНА", "ОК"))
            .setData { nameWeek ->

            }.show(childFragmentManager, null)
    }
}