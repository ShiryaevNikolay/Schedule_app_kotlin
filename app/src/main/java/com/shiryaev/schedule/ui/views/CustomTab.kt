package com.shiryaev.schedule.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import com.shiryaev.schedule.R
import com.shiryaev.schedule.tools.adapters.CustomTabAdapter
import com.shiryaev.schedule.tools.interfaces.OnClickCustomTabListener
import com.shiryaev.schedule.ui.views.utils.*

class CustomTab @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var positionItem = 0
    private var selectedItem = 0
    private val cardTab: MaterialCardView
    private val textTab: AppCompatTextView

    private lateinit var onClickTab: OnClickCustomTabListener

    init {
        inflate(context, R.layout.custom_tab, this)

        cardTab = this.findViewById(R.id.tab_card)
        textTab = this.findViewById(R.id.tab_tv)

        cardTab.setOnClickListener { onClickTab.onClickCustomTab(positionItem) }

        this.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
        )

        attrs.let {
            val a = context.theme.obtainStyledAttributes(
                    attrs, R.styleable.CustomTab, 0, 0
            )
            val text = a.getString(R.styleable.CustomTab_text).toString()
            a.recycle()
            setText(text)
        }
    }

    fun setText(text: String) { textTab.text = text }

    fun setPosition(position: Int) { this.positionItem = position }

    fun setSelectedItem(selected: Int) {
        this.selectedItem = selected
        if (this.selectedItem == positionItem) {
            setSelectedColor()
        } else {
            setUnselectedColor()
        }
    }

    fun serAdapter(adapter: CustomTabAdapter) {
        onClickTab = adapter
    }

    @SuppressLint("ResourceType")
    private fun setSelectedColor() {
        val invertedRed = 255 - Color.red(textTab.textColors.defaultColor)
        val invertedGreen = 255 - Color.green(textTab.textColors.defaultColor)
        val invertedBlue = 255 - Color.blue(textTab.textColors.defaultColor)

        cardTab.setCardBackgroundColor(fetchColorPrimary(context))
        textTab.setTextColor(Color.rgb(invertedRed, invertedGreen, invertedBlue))
    }

    @SuppressLint("ResourceType")
    private fun setUnselectedColor() {
        cardTab.setCardBackgroundColor(Color.TRANSPARENT)
        textTab.setTextColor(fetchColorText(context))
    }
}