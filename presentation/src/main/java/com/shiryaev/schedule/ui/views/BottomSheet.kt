package com.shiryaev.schedule.ui.views

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.shiryaev.domain.models.Note
import com.shiryaev.schedule.R
import com.shiryaev.schedule.common.controllers.ItemNoteController
import ru.surfstudio.android.easyadapter.EasyAdapter
import ru.surfstudio.android.easyadapter.ItemList

class BottomSheet @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    var onChangeState: ((Int) -> Unit)? = null

    private var mMarginTop: Int = 112

    private val mEasyAdapter = EasyAdapter()
    private val mBottomSheetBehavior: BottomSheetBehavior<LinearLayoutCompat>
    private lateinit var mBottomSheet: LinearLayoutCompat
    private lateinit var mLayoutCard: MaterialCardView
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mProgress: CircularProgressIndicator
    private lateinit var mInfoError: LinearLayoutCompat

    init {
        inflate(context, R.layout.bottom_sheet_list, this)

        initView()

        initRecyclerView()

        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            peekHeight = 600
            isHideable = false
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    onChangeState?.invoke(newState)
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
        }
    }

//    fun setPeekHeight(height: Int) {
//        mBottomSheetBehavior.peekHeight = height
//    }

    fun setNoteList(notes: List<Note>): Boolean {
        val itemNote = ItemNoteController().apply {
            onClickNote = { note ->
                // TODO
            }
        }
        val itemList = ItemList.create().apply {
            addAll(notes, itemNote)
        }
        mEasyAdapter.setItems(itemList)
        return mEasyAdapter.itemCount == 0
    }

    fun setLoading(value: Boolean) {
        mProgress.isVisible = value
    }

    fun setInfoError(value: Boolean) {
        mInfoError.isVisible = value
    }

    fun setMarginTop(height: Int) {
        val params = LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.MATCH_PARENT,
                LinearLayoutCompat.LayoutParams.MATCH_PARENT
        ).apply {
            topMargin = 112.dp + height
        }
        mLayoutCard.layoutParams = params
    }

    private fun initView() {
        mBottomSheet = findViewById(R.id.bottom_sheet)
        mLayoutCard = findViewById(R.id.layout_card)
        mRecyclerView = findViewById(R.id.recycler_view)
        mProgress = findViewById(R.id.calendar_note_pb)
        mInfoError = findViewById(R.id.info_tv)
    }

    private fun initRecyclerView() {
        with (mRecyclerView) {
            adapter = mEasyAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
}