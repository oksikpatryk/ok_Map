package com.oksik.okmap.ui.plant_search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oksik.okmap.databinding.PlantSearchItemListBinding
import com.oksik.okmap.model.Plant

class PlantSearchListAdapter : ListAdapter<Plant, PlantSearchListAdapter.ViewHolder>(PlatListDiffCallback()) {

    class ViewHolder private constructor(val binding: PlantSearchItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Plant) {
            binding.plant = item
            binding.executePendingBindings()
        }

        companion object {
            fun form(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PlantSearchItemListBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.form(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PlatListDiffCallback : DiffUtil.ItemCallback<Plant>() {
    override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
        return oldItem == newItem
    }

}