package com.example.zomatorsearchapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatorsearchapp.databinding.RowRestaurantBinding
import com.example.zomatorsearchapp.model.Establishment
import com.example.zomatorsearchapp.model.Restaurant

class RestaurantsRecyclerViewAdapter(private val restaurants:List<Establishment>) :
    RecyclerView.Adapter<RestaurantsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RowRestaurantBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(restaurants[position].restaurant)
    }

    override fun getItemCount(): Int {
       return restaurants.size
    }
    class ViewHolder(val rowBinding : RowRestaurantBinding):RecyclerView.ViewHolder(rowBinding.root){
        fun bind(restaurant:Restaurant){
            rowBinding.restaurant = restaurant
            rowBinding.executePendingBindings()
        }
    }
}