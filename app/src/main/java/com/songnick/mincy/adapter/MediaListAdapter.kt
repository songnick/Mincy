package com.songnick.mincy.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.songnick.mincy.R
import com.songnick.mincy.data.MediaData
import com.songnick.mincy.databinding.MediaGridItemBinding
import com.songnick.mincy.viewmodel.MediaItemViewModel

class MediaListAdapter: ListAdapter<MediaData, MediaListAdapter.ViewHolder>(DiffMediaCallback()) {


    class ViewHolder(
        private val binding: MediaGridItemBinding
    ):RecyclerView.ViewHolder(binding.root){
        fun binding(data:MediaData){
            val layoutParams = binding.root.layoutParams
            layoutParams.height = 160
            binding.root.layoutParams = layoutParams
            with(binding){
                viewModel = MediaItemViewModel(data)
                executePendingBindings()
            }
        }
    }

    private class DiffMediaCallback:DiffUtil.ItemCallback<MediaData>(){
        override fun areItemsTheSame(oldItem: MediaData, newItem: MediaData): Boolean {

            return oldItem.path == newItem.path
        }

        override fun areContentsTheSame(oldItem: MediaData, newItem: MediaData): Boolean {

            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(DataBindingUtil.
        inflate(LayoutInflater.from(parent.context), R.layout.media_grid_item, parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.binding(getItem(position))
    }
}