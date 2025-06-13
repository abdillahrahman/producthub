package com.dicoding.producthub.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.producthub.R
import com.dicoding.producthub.core.data.Resource
import com.dicoding.producthub.core.domain.model.Product
import com.dicoding.producthub.core.ui.ProductAdapter
import com.dicoding.producthub.databinding.FragmentHomeBinding
import com.dicoding.producthub.detail.DetailProductActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

@Suppress("DEPRECATION")
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var originalProductList: List<Product> = listOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)

        if (activity != null) {
            val productAdapter = ProductAdapter()
            productAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailProductActivity::class.java)
                intent.putExtra(DetailProductActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            homeViewModel.product.observe(viewLifecycleOwner) { product ->
                if (product != null) {
                    when (product) {
                        is Resource.Loading -> binding.progresBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progresBar.visibility = View.GONE
                            product.data?.let { data ->
                                originalProductList = data
                                productAdapter.submitList(data)
                            }
                        }

                        is Resource.Error -> {
                            binding.progresBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                        }
                    }
                }
            }

            with(binding.rvProduct) {
                layoutManager = GridLayoutManager(context, 2)
                setHasFixedSize(true)
                adapter = productAdapter
            }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.actionSearch)
        val searchView = searchItem.actionView as SearchView

        searchView.queryHint = getString(R.string.query_hint_home)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun filterList(query: String?) {
        val filteredList = if (!query.isNullOrEmpty()) {
            originalProductList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        } else {
            originalProductList
        }

        (binding.rvProduct.adapter as ProductAdapter).submitList(filteredList)
    }
}