package com.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TableLayout

class CustomTabLayout(
        context: Context?,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    init {
        orientation = HORIZONTAL
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun addItems(listItems: ArrayList<CustomTab>) {
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