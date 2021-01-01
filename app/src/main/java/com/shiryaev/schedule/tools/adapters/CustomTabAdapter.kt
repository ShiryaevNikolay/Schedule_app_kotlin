package com.shiryaev.schedule.tools.adapters

import android.content.Context
import android.view.View
import android.widget.TableLayout
import com.shiryaev.schedule.R
import com.shiryaev.schedule.tools.interfaces.OnClickCustomTabListener
import com.shiryaev.schedule.ui.views.CustomTab
import com.shiryaev.schedule.ui.views.CustomTabLayout

class CustomTabAdapter(
    private val context: Context,
    private var selectedTab: Int = 0
) : OnClickCustomTabListener {

    private val tabs = ArrayList<CustomTab>()

    fun setLayout(rootLayout: CustomTabLayout) { initTabs(rootLayout) }

    override fun onClickCustomTab(positionTab: Int) {
        tabs[selectedTab].setSelectedItem(positionTab)
        selectedTab = positionTab
        tabs[positionTab].setSelectedItem(positionTab)
    }

    private fun initTabs(rootLayout: CustomTabLayout) {
        val arrayTextTab = context.resources.getStringArray(R.array.days_of_week)
        val lenList = arrayTextTab.size
        for (i in arrayTextTab.indices) {
            rootLayout.addView(CustomTab(context).apply {
                setText(arrayTextTab[i])
                setPosition(i)
                setSelectedItem(selectedTab)
                serAdapter(this@CustomTabAdapter)
                tabs.add(this)
            })
            if (lenList > 1 && i != lenList - 1) {
                rootLayout.addView(View(context).apply { layoutParams = TableLayout.LayoutParams(0, 0, 1f) })
            }
        }
    }
}