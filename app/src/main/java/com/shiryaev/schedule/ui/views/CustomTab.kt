package com.shiryaev.schedule.ui.views

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import com.shiryaev.schedule.R


class CustomTab @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var onTabClickListener: (() -> Unit)? = null

    private var text: String = ""

    init {
        inflate(context, R.layout.custom_tab, this)
        this.findViewById<MaterialCardView>(R.id.tab_card).setOnClickListener { onTabClickListener?.invoke() }

        this.layoutParams = LinearLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT,
//                1.0f
        )

        attrs.let {
            val a = context.theme.obtainStyledAttributes(
                    attrs, R.styleable.CustomTab, 0, 0
            )
            text = a.getString(R.styleable.CustomTab_text).toString()
            a.recycle()
            setText(text)
        }
    }

    fun setUnselectedColor() {
        val states = arrayOf(
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_enabled),
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(android.R.attr.state_pressed)
        )

        val colors = intArrayOf(
                Color.TRANSPARENT,
                Color.RED,
                Color.GREEN,
                Color.BLUE
        )

        val myList = ColorStateList(states, colors)

        this.findViewById<MaterialCardView>(R.id.tab_card).setCardBackgroundColor(myList)
    }

    fun setText(text: String) {
        this.findViewById<TextView>(R.id.tab_tv).text = text
    }
}