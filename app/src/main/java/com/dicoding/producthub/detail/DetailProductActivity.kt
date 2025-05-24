package com.dicoding.producthub.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.dicoding.producthub.R

import com.dicoding.producthub.core.domain.model.Product
import com.dicoding.producthub.databinding.ActivityDetailProductBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


@Suppress("DEPRECATION")
class DetailProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProductBinding

    private val detailProductViewModel: DetailProductViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailProduct = intent.getParcelableExtra<Product>(EXTRA_DATA)
        showDetailProduct(detailProduct)
    }

    private fun showDetailProduct(detailProduct: Product?) {
        detailProduct?.let {
            supportActionBar?.title = detailProduct.title
            binding.tvItemPrice.text =
                getString(R.string.product_price, detailProduct.price.toString())
            binding.tvItemName.text = detailProduct.title
            binding.itemDescription.text = detailProduct.description
            Glide.with(this@DetailProductActivity)
                .load(detailProduct.image)
                .into(binding.ivDetailImage)

            var statusFavorite = detailProduct.isFavorite
            setStatusFavorite(statusFavorite)
            binding.fab.setOnClickListener {
                statusFavorite = !statusFavorite
                detailProductViewModel.setFavoriteProduct(detailProduct, statusFavorite)
                setStatusFavorite(statusFavorite)
            }
        }
    }

    private fun setStatusFavorite(statusFavorite: Boolean) {
        if (statusFavorite) {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_favorite_white
                )
            )
        } else {
            binding.fab.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_not_favorite_white
                )
            )
        }
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}