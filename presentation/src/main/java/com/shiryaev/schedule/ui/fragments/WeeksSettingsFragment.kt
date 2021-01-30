package com.shiryaev.schedule.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiryaev.data.common.CustomFactory
import com.shiryaev.data.viewmodels.WeekSettingsViewModel
import com.shiryaev.domain.models.Week
import com.shiryaev.domain.utils.UtilsKeys
import com.shiryaev.schedule.R
import com.shiryaev.schedule.common.controllers.ItemWeekController
import com.shiryaev.schedule.databinding.FrWeeksSettingsBinding
import com.shiryaev.schedule.ui.dialogs.ColorPickerDialog
import com.shiryaev.schedule.ui.dialogs.FieldDialog
import com.shiryaev.schedule.ui.dialogs.InfoDialog
import com.shiryaev.schedule.ui.dialogs.OnClickButtonDialogListener
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class WeeksSettingsFragment : Fragment(), OnClickButtonDialogListener {

    private val mEasyAdapter = EasyAdapter()

    private var _binding: FrWeeksSettingsBinding? = null
    private val binding get() = _binding!!

    private var mContext: Context? = null
    private lateinit var mNavController: NavController
    private lateinit var mViewModel: WeekSettingsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        mViewModel = ViewModelProvider(this, CustomFactory(WeekSettingsViewModel())).get(WeekSettingsViewModel::class.java)
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

        // Синхронизируем xml с viewModel
        with(binding) {
            vm = mViewModel
            lifecycleOwner = this@WeeksSettingsFragment
        }

        mViewModel.getWeeks().observe(viewLifecycleOwner) { weeks ->
            setListToAdapter(weeks)
        }

        initRecyclerView()

        binding.toolbar.setNavigationOnClickListener { mNavController.popBackStack() }

        binding.fab.setOnClickListener { showFieldDialog() }

        return binding.root
    }

    private fun initRecyclerView() {
        with(binding.recyclerView) {
            adapter = mEasyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setListToAdapter(weeks: List<Week>) {
        val itemWeek = ItemWeekController().apply {
            onClickLayout = { week -> showFieldDialog(week = week) }
            onLongClickLayout = { week -> showRemoveDialog(week) }
            onCLickIndicatorBtn = { week -> showColorPickerDialog(week) }
        }
        val listWeek = ItemList.create().apply {
            addAll(weeks, itemWeek)
        }
        mEasyAdapter.setItems(listWeek)
        mViewModel.setIsErrorVisible(mEasyAdapter.itemCount == 0)
    }

    private fun showFieldDialog(week: Week? = null) {
        FieldDialog()
            .setHeader(mContext?.resources?.getString(R.string.enter_the_name_of_the_week)!!)
            .setButton(listOf(
                    mContext?.resources?.getStringArray(R.array.button_dialog)!!.first(),
                    mContext?.resources?.getStringArray(R.array.button_dialog)!!.last()
            ))
            .setData(week = week)
            .show(childFragmentManager, UtilsKeys.FIELD_DIALOG.name)
    }

    private fun showRemoveDialog(week: Week) {
        InfoDialog()
            .setHeader(mContext?.resources?.getString(R.string.delete_week)!!)
            .setButton(listOf(
                mContext?.resources?.getStringArray(R.array.button_dialog)!!.first(),
                mContext?.resources?.getStringArray(R.array.button_dialog)!!.last()
            ))
            .setData(week = week)
            .show(childFragmentManager, UtilsKeys.INFO_DIALOG.name)
    }

    private fun showColorPickerDialog(week: Week) {
        ColorPickerDialog()
                .setHeader(mContext?.resources?.getString(R.string.choose_color)!!)
                .setButton(listOf(
                        mContext?.resources?.getString(R.string.dumping)!!,
                        mContext?.resources?.getStringArray(R.array.button_dialog)!!.first(),
                        mContext?.resources?.getStringArray(R.array.button_dialog)!!.last()
                ))
                .setData(week)
                .show(childFragmentManager, UtilsKeys.COLOR_PICK_DIALOG.name)
    }

    override fun onClick(text: String, week: Week?, dialog: String) {
        when(dialog) {
            UtilsKeys.FIELD_DIALOG.name -> {
                if (week == null) {
                    mViewModel.insertWeek(Week(mName = text))
                } else {
                    mViewModel.updateWeek(week)
                }
            }
            UtilsKeys.INFO_DIALOG.name -> {
                week?.let { mViewModel.deleteWeek(it) }
            }
            UtilsKeys.COLOR_PICK_DIALOG.name -> {
                if (week != null) {
                    mViewModel.updateWeek(week)
                }
            }
        }
    }
}