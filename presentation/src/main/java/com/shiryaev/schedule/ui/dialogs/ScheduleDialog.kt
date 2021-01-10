package com.shiryaev.schedule.ui.dialogs

import com.shiryaev.data.common.models.ItemDialog
import com.shiryaev.domain.models.Schedule

class ScheduleDialog(
        private val onClickItemDialog: (schedule: Schedule, positionItemDialog: Int) -> Unit
) : CustomDialog() {

    private lateinit var mSchedule: Schedule

    fun setData(schedule: Schedule, listItem: List<ItemDialog>) {
        mSchedule = schedule
        mItemDialogController.onCLickListener = { positionItem ->
            onClickItemDialog.invoke(mSchedule, positionItem)
            dismiss()
        }
        setListToAdapter(listItem)
    }
}