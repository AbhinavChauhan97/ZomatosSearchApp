package com.example.zomatorsearchapp.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.zomatorsearchapp.R
import com.example.zomatorsearchapp.adapters.RestaurantsPagedListAdapter
import com.example.zomatorsearchapp.databinding.FragmentSearchedRestaurantsBinding
import com.example.zomatorsearchapp.model.Establishment
import com.example.zomatorsearchapp.repository.RetrofitClient
import com.example.zomatorsearchapp.viewmodel.RestaurantsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class RestaurantsListFragment : Fragment(R.layout.fragment_searched_restaurants) {

    lateinit var viewModel: RestaurantsViewModel
    lateinit var binding: FragmentSearchedRestaurantsBinding
    private  var adapter: RestaurantsPagedListAdapter = RestaurantsPagedListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(RestaurantsViewModel::class.java)
        binding = FragmentSearchedRestaurantsBinding.bind(view)
        binding.searchedRestaurantsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.searchedRestaurantsRecyclerView.addItemDecoration(DividerItemDecoration(requireActivity(), LinearLayoutManager.VERTICAL))
        binding.searchedRestaurantsRecyclerView.setHasFixedSize(true)

        if (viewModel.hasResults) {
            adapter.submitList(null)
            adapter.submitList(viewModel.restaurantsPagedList.value)
        }
        binding.searchedRestaurantsRecyclerView.adapter = adapter
        setupSearchView()
    }

    private fun setupSearchView() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.length > 2) {
                    binding.progressBar.isVisible = true
                    lifecycleScope.launch {
                        val fetchedSuccessfully = viewModel.fetchResultsFor(query)
                        if(fetchedSuccessfully) {
                            viewModel.restaurantsPagedList.observe(requireActivity(),{ adapter.submitList(it) })
                            binding.progressBar.isVisible = false
                        }
                        else{
                            Toast.makeText(requireActivity(), R.string.nothing_found, Toast.LENGTH_SHORT).show()
                        }
                        binding.progressBar.isVisible = false
                    }
                } else {
                    Toast.makeText(requireActivity(), R.string.short_query, Toast.LENGTH_SHORT).show()
                }
                binding.msg.isVisible = false
                return true
            }
            override fun onQueryTextChange(newText: String?) = true

        })
    }
}
