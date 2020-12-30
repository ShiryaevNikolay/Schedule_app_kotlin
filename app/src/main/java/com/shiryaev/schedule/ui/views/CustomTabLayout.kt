package com.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.shiryaev.schedule.R

class CustomTabLayout(
        context: Context?,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var selectedItem = 0

    init {
        orientation = HORIZONTAL

        this.layoutParams = LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        val listItems = ArrayList<CustomTab>()
        val arrayTextTab = context?.resources?.getStringArray(R.array.days_of_week)
        if (arrayTextTab != null) {
            for (item in arrayTextTab) {
                listItems.add(CustomTab(context).apply { setText(item) })
            }
        }
        initItems(listItems)
    }

    private fun initItems(listItems: ArrayList<CustomTab>) {
        val lenList = listItems.size
        for (i in 0 until lenList) {
            this.addView(listItems[i])
            if (lenList > 1 && i != lenList - 1) {
                val newView = View(context)
                val param = TableLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f)
                newView.layoutParams = param
                this.addView(newView)
            }
        }
    }
}