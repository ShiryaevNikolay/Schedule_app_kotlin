package com.shiryaev.schedule.ui.views.utils

import android.content.Context
import android.content.res.TypedArray
import android.util.TypedValue
import com.shiryaev.schedule.R

fun fetchColorPrimary(mContext: Context) : Int {
    val typedValue = TypedValue()
    val a: TypedArray = mContext.obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.colorPrimary))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun fetchColorBackground(mContext: Context) : Int {
    val typedValue = TypedValue()
    val a: TypedArray = mContext.obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.colorBackground))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun fetchColorText(mContext: Context) : Int {
    val typedValue = TypedValue()
    val a: TypedArray = mContext.obtainStyledAttributes(typedValue.data, intArrayOf(android.R.attr.textColorPrimary))
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}