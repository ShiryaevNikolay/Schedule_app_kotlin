package com.shiryaev.schedule.ui.views.utils

import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceFirstItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val space = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin.toFloat(), view.resources.displayMetrics).toInt()
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space
        }
    }
}