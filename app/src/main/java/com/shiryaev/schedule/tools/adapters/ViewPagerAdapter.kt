package com.shiryaev.schedule.tools.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.shiryaev.schedule.databinding.FrPageScheduleBinding
import com.shiryaev.schedule.ui.fragments.PageScheduleFragment

class ViewPagerAdapter : RecyclerView.Adapter<ViewPagerAdapter.PagerVH>() {

    private var listPage = ArrayList<PageScheduleFragment>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = FrPageScheduleBinding.inflate(layoutInflater, parent, false)
        return PagerVH(itemView)
    }

    override fun onBindViewHolder(holder: PagerVH, position: Int) = holder.itemView.run {
        // Todo
    }

    override fun getItemCount(): Int = 3

    inner class PagerVH(private val itemView: FrPageScheduleBinding) : RecyclerView.ViewHolder(itemView.root)
}