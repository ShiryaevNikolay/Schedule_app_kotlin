package com.shiryaev.schedule.ui.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.shiryaev.schedule.R

class BottomSheet @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val mBottomSheetBehavior: BottomSheetBehavior<MaterialCardView>
    private lateinit var mBottomSheet: MaterialCardView

    init {
        inflate(context, R.layout.bottom_sheet_list, this)

        initView()

        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            state = BottomSheetBehavior.STATE_EXPANDED
            state = BottomSheetBehavior.STATE_HIDDEN
            peekHeight = 540
            isHideable = false
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
//                    TODO("Not yet implemented")
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                    TODO("Not yet implemented")
                }
            })
        }
    }

//    fun setPeekHeight(height: Int) {
//        mBottomSheetBehavior.peekHeight = height
//    }

    private fun initView() {
        mBottomSheet = findViewById(R.id.bottom_sheet)
    }
}