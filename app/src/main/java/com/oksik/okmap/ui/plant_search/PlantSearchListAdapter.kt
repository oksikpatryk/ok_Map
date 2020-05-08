package com.oksik.okmap.ui.plant_search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oksik.okmap.databinding.PlantSearchItemListBinding
import com.oksik.okmap.model.Plant

class PlantSearchListAdapter(val plantSearchClickListener: PlantSearchClickListener) :
    ListAdapter<Plant, PlantSearchListAdapter.ViewHolder>(PlatListDiffCallback()), Filterable {
    var filteredList: MutableList<Plant> = currentList
    var plantList: MutableList<Plant>? = null

    class ViewHolder private constructor(val binding: PlantSearchItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Plant, plantSearchClickListener: PlantSearchClickListener) {
            binding.plant = item
            binding.clickListener = plantSearchClickListener
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
        holder.bind(getItem(position), plantSearchClickListener)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                if (plantList == null) {
                    plantList = currentList
                }
                filteredList = if (constraint.toString().isEmpty()) {
                    plantList as MutableList<Plant>
                } else {
                    plantList!!.filter { plant -> plant.name!!.contains(constraint.toString().trim(), ignoreCase = true) }
                        .toMutableList()
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                submitList(results.values as MutableList<Plant>)
                notifyDataSetChanged()
            }

        }
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

class PlantSearchClickListener(val clickListener: (plant: Plant) -> Unit){
    fun onClick(plant: Plant) = clickListener(plant)
}
