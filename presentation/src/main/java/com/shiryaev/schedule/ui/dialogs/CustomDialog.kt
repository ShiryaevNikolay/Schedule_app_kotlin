package com.shiryaev.schedule.ui.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shiryaev.domain.models.Schedule
import com.shiryaev.schedule.common.controllers.ItemDialogController
import com.shiryaev.data.common.models.ItemDialog
import com.shiryaev.schedule.databinding.CustomLayoutDialogBinding
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class CustomDialog(
        private val onClickItemDialog: (schedule: Schedule, positionItemDialog: Int) -> Unit
) : DialogFragment() {

    private var _binding: CustomLayoutDialogBinding? = null
    private val binding get() = _binding!!

    private val mEasyAdapter = EasyAdapter()
    private lateinit var mItemDialogController: ItemDialogController

    private lateinit var mSchedule: Schedule
    private lateinit var mDialogList: ItemList

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = CustomLayoutDialogBinding.inflate(inflater, container, false)

        // Устанавливаем прозрачность в 75%
        binding.layoutDialog.background.alpha = 192

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        initLayout()
        initList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setData(schedule: Schedule, listItem: List<ItemDialog>) {
        mSchedule = schedule
        setListToAdapter(listItem)
    }

    private fun setListToAdapter(listItem: List<ItemDialog>) {
        mItemDialogController = ItemDialogController { positionItem ->
            onClickItemDialog.invoke(mSchedule, positionItem)
        }
        mDialogList = ItemList.create().apply {
            addAll(listItem, mItemDialogController)
        }
        mEasyAdapter.setItems(mDialogList)
    }

    private fun initList() {
        with(binding.dialogRv) {
            adapter = mEasyAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun initLayout() {
        // Устанавливаем диалог в низ экрана
        val p = dialog!!.window!!.attributes
        p.width = ViewGroup.LayoutParams.MATCH_PARENT
        dialog!!.window!!.apply {
            attributes = p
            setGravity(Gravity.BOTTOM)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }
}