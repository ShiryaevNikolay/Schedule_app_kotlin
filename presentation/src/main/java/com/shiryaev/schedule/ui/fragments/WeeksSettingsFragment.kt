package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.shiryaev.schedule.R
import com.shiryaev.schedule.databinding.FrWeeksSettingsBinding
import com.shiryaev.schedule.ui.dialogs.FieldDialog

class WeeksSettingsFragment : Fragment() {

    private var _binding: FrWeeksSettingsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mNavController: NavController
    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

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
            .setButton(listOf(
                    mContext?.resources?.getStringArray(R.array.button_dialog)!!.first(),
                    mContext?.resources?.getStringArray(R.array.button_dialog)!!.last()
            ))
            .setData { nameWeek ->

            }.show(childFragmentManager, null)
    }
}