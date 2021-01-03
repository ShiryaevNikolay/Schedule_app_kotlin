package com.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.shiryaev.schedule.R

class TopBar(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : TabLayout(context, attrs, defStyleAttr) {
    override fun setupWithViewPager(viewPager: ViewPager?) {
        super.setupWithViewPager(viewPager)
    }
}