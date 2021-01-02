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
import com.shiryaev.schedule.tools.interfaces.OnClickCustomTabListener

class CustomTabLayout(
        context: Context?,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs), OnClickCustomTabListener {

    var setSelectedPage: ((selectedTab: Int) -> Unit)? = null

    private var selectedItem = 0
    private lateinit var adapter: CustomTabAdapter

    init {
        orientation = HORIZONTAL

        this.layoutParams = LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        if (context != null) {
            adapter = CustomTabAdapter(context, this, selectedItem).apply { setLayout(this@CustomTabLayout) }
        }
    }

    override fun onClickCustomTab(positionTab: Int) {
        this.selectedItem = positionTab
        setSelectedPage?.invoke(positionTab)
    }

    fun setSelectedTab(positionPage: Int) {
        adapter.setSelectedTab(positionPage)
    }
}