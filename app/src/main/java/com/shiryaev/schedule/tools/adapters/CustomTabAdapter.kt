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
        private val onClickTab: OnClickCustomTabListener,
        private var selectedTab: Int = 0,
) : OnClickCustomTabListener {

    private var countTab = 0
    private val tabs = ArrayList<CustomTab>()

    fun setLayout(rootLayout: CustomTabLayout) { initTabs(rootLayout) }

    override fun onClickCustomTab(positionTab: Int) {
        setSelectedTab(positionTab)

        onClickTab.onClickCustomTab(positionTab)
    }

    fun setSelectedTab(selectedTab: Int) {
        // Меняем индикатор выбранного таба
        tabs[this.selectedTab].setSelectedItem(selectedTab)
        this.selectedTab = selectedTab
        tabs[this.selectedTab].setSelectedItem(selectedTab)
    }

    fun setCountTab(countTab: Int) { this.countTab = countTab }

    private fun initTabs(rootLayout: CustomTabLayout) {
        val arrayTextTab = context.resources.getStringArray(R.array.days_of_week)
        for (i in 0 until countTab) {
            rootLayout.addView(CustomTab(context).apply {
                setText(arrayTextTab[i])
                setPosition(i)
                setSelectedItem(selectedTab)
                serAdapter(this@CustomTabAdapter)
                tabs.add(this)
            })
            if (countTab > 1 && i != countTab - 1) {
                rootLayout.addView(View(context).apply { layoutParams = TableLayout.LayoutParams(0, 0, 1f) })
            }
        }
    }
}