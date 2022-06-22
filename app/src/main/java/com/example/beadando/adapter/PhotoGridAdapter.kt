package com.example.beadando.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beadando.network.Photos
import com.example.beadando.databinding.GridViewItemBinding

class PhotoGridAdapter: ListAdapter<Photos, PhotoGridAdapter.MarsPhotosViewHolder>(DiffCallback)
    {

        class MarsPhotosViewHolder(
            private var binding: GridViewItemBinding
        ) : RecyclerView.ViewHolder(binding.root) {
            fun bind(photos: Photos) {
                binding.photo = photos
                // This is important, because it forces the data binding to execute immediately,
                // which allows the RecyclerView to make the correct view size measurements
                binding.executePendingBindings()
            }
        }

        companion object DiffCallback : DiffUtil.ItemCallback<Photos>() {
            override fun areItemsTheSame(oldItem: Photos, newItem: Photos): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Photos, newItem: Photos): Boolean {
                return oldItem.imgSrcUrl == newItem.imgSrcUrl
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MarsPhotosViewHolder {
            return MarsPhotosViewHolder(
                GridViewItemBinding.inflate(LayoutInflater.from(parent.context))
            )
        }

        override fun onBindViewHolder(holder: MarsPhotosViewHolder, position: Int) {
            val photos = getItem(position)
            holder.bind(photos)
        }
    }