package com.example.zomatorsearchapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatorsearchapp.databinding.RowRestaurantBinding
import com.example.zomatorsearchapp.model.Establishment
import com.example.zomatorsearchapp.model.Restaurant

class RestaurantsPagedListAdapter:

    PagedListAdapter<Establishment, RestaurantsPagedListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowRestaurantBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val establishment = getItem(position)
        if(establishment != null){
           holder.bind(establishment.restaurant)
        }
    }
    class ViewHolder(val rowBinding : RowRestaurantBinding): RecyclerView.ViewHolder(rowBinding.root){
        fun bind(restaurant: Restaurant){
            rowBinding.restaurant = restaurant
            rowBinding.executePendingBindings()
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<Establishment>() {
        override fun areItemsTheSame(oldItem: Establishment, newItem: Establishment): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Establishment, newItem: Establishment): Boolean {
            return oldItem == newItem
        }

    }
}