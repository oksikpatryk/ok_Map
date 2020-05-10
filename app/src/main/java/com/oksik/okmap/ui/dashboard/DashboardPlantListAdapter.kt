package com.oksik.okmap.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oksik.okmap.databinding.DashboardNewPlantItemListBinding
import com.oksik.okmap.model.Plant
import com.oksik.okmap.ui.plant_search.PlatListDiffCallback

class DashboardPlantListAdapter :
    ListAdapter<Plant, DashboardPlantListAdapter.ViewHolder>(PlatListDiffCallback()) {
    class ViewHolder private constructor(val binding: DashboardNewPlantItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Plant) {
            binding.plant = item
        }

        companion object {
            fun form(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    DashboardNewPlantItemListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DashboardPlantListAdapter.ViewHolder {
        return ViewHolder.form(parent)
    }

    override fun onBindViewHolder(holder: DashboardPlantListAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

