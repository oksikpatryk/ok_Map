package com.oksik.okmap.ui.dialog_information

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oksik.okmap.databinding.InformationDialogImagesItemBinding

class PicturesAdapter : ListAdapter<String, PicturesAdapter.ViewHolder>(StringDiffCallback()) {
    class ViewHolder private constructor(val binding: InformationDialogImagesItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(url: String) {
            binding.plantImageUrl = url
        }

        companion object {
            fun form(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    InformationDialogImagesItemBinding.inflate(layoutInflater, parent, false)
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

class StringDiffCallback : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}