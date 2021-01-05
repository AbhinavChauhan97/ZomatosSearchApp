package com.example.zomatorsearchapp.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.zomatorsearchapp.R
import com.example.zomatorsearchapp.adapters.RestaurantsRecyclerViewAdapter
import com.example.zomatorsearchapp.databinding.FragmentSearchedRestaurantsBinding
import com.example.zomatorsearchapp.viewmodel.RestaurantsListFragmentViewModel

class RestaurantsListFragment : Fragment(R.layout.fragment_searched_restaurants) {

    lateinit var viewModel: RestaurantsListFragmentViewModel
    lateinit var binding: FragmentSearchedRestaurantsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RestaurantsListFragmentViewModel::class.java)
        binding = FragmentSearchedRestaurantsBinding.bind(view)

        binding.searchedRestaurantsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.searchedRestaurantsRecyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
        setupSearchView()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.whenInGivenLocationRestaurantResponseReceived(query) {
                        binding.searchedRestaurantsRecyclerView.adapter =
                            RestaurantsRecyclerViewAdapter(viewModel.restaurants)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true

        })
    }

    override fun onResume() {
        super.onResume()
        if(viewModel.containsRestaurants) {
            binding.searchedRestaurantsRecyclerView.adapter =
                RestaurantsRecyclerViewAdapter(viewModel.restaurants)
        }
    }
}
