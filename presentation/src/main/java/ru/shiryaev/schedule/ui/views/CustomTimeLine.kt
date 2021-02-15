package ru.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.AppCompatTextView
import ru.shiryaev.schedule.R

class CustomTimeLine @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

//    private val container: MaterialCardView
    private val mTextView: AppCompatTextView
    private val mLineTop: FrameLayout
    private val mLineBottom: FrameLayout

    init {
        inflate(context, R.layout.custom_timeline, this)

//        container = findViewById(R.id.item_time_container)
        mTextView = findViewById(R.id.item_time_tv)
        mLineTop = findViewById(R.id.line_top)
        mLineBottom = findViewById(R.id.line_bottom)
    }

    fun setText(text: String) { mTextView.text = text }

    fun setLineTopIsVisible(value: Boolean) {
        if (value) {
            mLineTop.visibility = View.VISIBLE
        } else {
            mLineTop.visibility = View.INVISIBLE
        }
    }

    fun setLineBottomIsVisible(value: Boolean) {
        if (value) {
            mLineBottom.visibility = View.VISIBLE
        } else {
            mLineBottom.visibility = View.INVISIBLE
        }
    }
}