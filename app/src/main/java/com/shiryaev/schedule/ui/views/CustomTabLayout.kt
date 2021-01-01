package com.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.shiryaev.schedule.R
import com.shiryaev.schedule.tools.adapters.CustomTabAdapter

class CustomTabLayout(
        context: Context?,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var selectedItem = 0
    private var viewPager2: ViewPager2? = null
    private lateinit var adapter: CustomTabAdapter

    init {
        orientation = HORIZONTAL

        this.layoutParams = LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        if (context != null) {
            adapter = CustomTabAdapter(context).apply { setLayout(this@CustomTabLayout) }
        }
    }
}