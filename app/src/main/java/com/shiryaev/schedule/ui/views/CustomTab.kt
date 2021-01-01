package com.shiryaev.schedule.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.card.MaterialCardView
import com.shiryaev.schedule.R
import com.shiryaev.schedule.tools.adapters.CustomTabAdapter
import com.shiryaev.schedule.tools.interfaces.OnClickCustomTabListener
import com.shiryaev.schedule.ui.views.utils.fetchColorBackground
import com.shiryaev.schedule.ui.views.utils.fetchColorPrimary
import com.shiryaev.schedule.ui.views.utils.fetchColorText


class CustomTab @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    var positionItem = 0

    private var selectedItem = 0
    private var text: String = ""
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
            text = a.getString(R.styleable.CustomTab_text).toString()
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
        // СДЕЛАТЬ ИВЕРСИЮ ЦВЕТА ТЕКСТА!
//        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
//        paint.style = Paint.Style.FILL_AND_STROKE
//        val cmData = floatArrayOf(
//                -1f, 0f, 0f, 0f, 255f,
//                0f, -1f, 0f, 0f, 255f,
//                0f, 0f, -1f, 0f, 255f,
//                0f, 0f, 0f, 1f, 0f)
//        val cm = ColorMatrix(cmData)
//        val filter = ColorMatrixColorFilter(cm)
//        paint.colorFilter = filter

        cardTab.setCardBackgroundColor(fetchColorPrimary(context))
//        textTab.setTextColor(fetchColorText(context))
//        textTab.setTextColor(paint.color)
    }

    @SuppressLint("ResourceType")
    private fun setUnselectedColor() {
        cardTab.setCardBackgroundColor(fetchColorBackground(context))
        textTab.setTextColor(fetchColorText(context))
    }
}