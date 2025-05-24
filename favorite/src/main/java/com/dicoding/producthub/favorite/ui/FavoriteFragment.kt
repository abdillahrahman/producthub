package com.dicoding.producthub.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.producthub.core.domain.model.Product
import com.dicoding.producthub.core.ui.ProductAdapter
import com.dicoding.producthub.detail.DetailProductActivity
import com.dicoding.producthub.favorite.R
import com.dicoding.producthub.favorite.databinding.FragmentFavoriteBinding
import com.dicoding.producthub.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@Suppress("DEPRECATION")
class FavoriteFragment : Fragment() {

    private val favoriteViewModel: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter
    private var originalProductList: List<Product> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadKoinModules(favoriteModule)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            productAdapter = ProductAdapter()
            productAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailProductActivity::class.java)
                intent.putExtra(DetailProductActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            favoriteViewModel.favoriteProduct.observe(viewLifecycleOwner) { dataFavorite ->
                originalProductList = dataFavorite
                productAdapter.submitList(dataFavorite)
                binding.viewEmpty.root.visibility =
                    if (dataFavorite.isNotEmpty()) View.GONE else View.VISIBLE
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

        searchView.queryHint = getString(R.string.query_hint_favorite)
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

    private fun filterList(query: String?) {
        val filteredList = if (!query.isNullOrEmpty()) {
            originalProductList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        } else {
            originalProductList
        }
        productAdapter.submitList(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
