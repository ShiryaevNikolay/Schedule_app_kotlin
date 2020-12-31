package com.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.shiryaev.schedule.R

class CustomTabLayout(
        context: Context?,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var selectedItem = 0
    private var viewPager2: ViewPager2? = null

    init {
        orientation = HORIZONTAL

        this.layoutParams = LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        if (context != null) { initItems(context) }
    }

//    fun setupWithViewPager(viewPager: ViewPager2) { setupWithViewPager(viewPager, false) }

//    fun setupWithViewPager(viewPager: ViewPager2, autoRefresh: Boolean) {
//        setupWithViewPager(viewPager, false, false)
//    }

//    private fun setupWithViewPager(viewPager: ViewPager2, autoRefresh: Boolean, implicitSetup: Boolean) {
//        if (this.viewPager2 != null) {
//            // Если мы уже настроили ViewPager, удалите нас из него
//            if (pageChangeListener != null) {
//                this.viewPager2.removeOnPageChangeListener(pageChangeListener)
//            }
//            if (adapterChangeListener != null) {
//                this.viewPager2.removeOnAdapterChangeListener(adapterChangeListener)
//            }
//        }
//    }

    private fun initItems(context: Context) {
        val listItems = ArrayList<CustomTab>()
        val arrayTextTab = context.resources.getStringArray(R.array.days_of_week)
        for (item in arrayTextTab) {
            listItems.add(CustomTab(context).apply { setText(item) })
        }
        addItems(listItems)
    }

    private fun addItems(listItems: ArrayList<CustomTab>) {
        val lenList = listItems.size
        for (i in 0 until lenList) {
            this.addView(listItems[i])
            if (lenList > 1 && i != lenList - 1) {
                val newView = View(context)
                val param = TableLayout.LayoutParams(0, 0, 1f)
                newView.layoutParams = param
                this.addView(newView)
            }
        }
    }
}