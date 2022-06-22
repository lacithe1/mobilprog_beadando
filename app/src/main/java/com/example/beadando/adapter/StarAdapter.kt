package com.example.beadando.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.beadando.data.Star
import com.example.beadando.databinding.StarListItemBinding


class StarAdapter(private val onItemClicked: (Star) -> Unit) :
    ListAdapter<Star, StarAdapter.StarListViewHolder>(DiffCallback) {

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Star>() {
            override fun areItemsTheSame(oldItem: Star, newItem: Star): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Star, newItem: Star): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarListViewHolder {
        val viewHolder = StarListViewHolder(
            StarListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: StarListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class StarListViewHolder(private var binding: StarListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(star: Star) {
            binding.starName.text = star.starName
            binding.watchTower.text = star.watchTower.toString()
        }
    }
}