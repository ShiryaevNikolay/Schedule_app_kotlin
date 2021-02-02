package com.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.view.isVisible
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView
import com.shiryaev.schedule.R
import com.shiryaev.schedule.ui.views.utils.showTabs


class TopBar @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), View.OnClickListener {

    var onChangeCurrentItem: ((Int) -> Unit)? = null
    var onChangeHeight: ((Int) -> Unit)? = null

    private var showTabs = false

    private val countPage: Int = context.resources.getStringArray(R.array.days_of_week).size

    private lateinit var mCardLayout: MaterialCardView
    private lateinit var mDay: MaterialTextView
    private lateinit var mShowTabLayoutBtn: AppCompatImageButton
    private lateinit var mCalendarBtn: AppCompatImageButton
    private lateinit var mTabLayout: CustomTabLayout

    init {
        inflate(context, R.layout.custom_topbar, this)

        initViews()

        initTabLayout()

        setClickListener()

        onChangeHeight?.invoke(mCardLayout.height)
//        setChangeHeight()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        onChangeHeight?.invoke(h)
//        setChangeHeight()
    }

    fun setSelectedTab(position: Int) {
        mTabLayout.setSelectedTab(position)
    }

    private fun initViews() {
        mCardLayout = findViewById(R.id.top_bar_layout)
        mDay = findViewById(R.id.top_bar_title)
        mShowTabLayoutBtn = findViewById(R.id.top_bar_show_tabs_btn)
        mCalendarBtn = findViewById(R.id.change_view_btn)
        mTabLayout = findViewById(R.id.custom_tab_layout)
    }

    private fun initTabLayout() {
        with(mTabLayout) {
            setCountTab(countPage)
            setSelectedPage = { selectedTab ->
                onChangeCurrentItem?.invoke(selectedTab)
            }
            getSelectedTab = { selectedTab ->
                setTextTitle(selectedTab)
            }
        }
    }

    private fun setTextTitle(position: Int) {
        val arrayTextTitle = context.resources.getStringArray(R.array.full_days_of_week)
        mDay.text = arrayTextTitle[position]
    }

    private fun setClickListener() {
        mShowTabLayoutBtn.setOnClickListener(this)
        mDay.setOnClickListener(this)
        mCalendarBtn.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.top_bar_show_tabs_btn, R.id.top_bar_title -> {
                showTabs = showTabs(context, mShowTabLayoutBtn, !showTabs)
                mTabLayout.isVisible = showTabs
//                setChangeHeight()
            }
            R.id.change_view_btn -> {
                // TODO: show calendar
            }
        }
    }

    private fun setChangeHeight() {
        mCardLayout.viewTreeObserver.addOnGlobalLayoutListener(
                object : OnGlobalLayoutListener {
                    override fun onGlobalLayout() {
                        onChangeHeight?.invoke(mCardLayout.height)
                        mCardLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
        )
    }
}