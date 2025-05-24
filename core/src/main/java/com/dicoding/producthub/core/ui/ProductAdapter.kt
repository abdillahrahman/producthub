package com.dicoding.producthub.core.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.producthub.core.R
import com.dicoding.producthub.core.databinding.ItemListProductBinding
import com.dicoding.producthub.core.domain.model.Product

class ProductAdapter : ListAdapter<Product, ProductAdapter.ListViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Product) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            ItemListProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    inner class ListViewHolder(private var binding: ItemListProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Product) {
            Glide.with(itemView.context)
                .load(data.image)
                .into(binding.ivItemImage)
            binding.tvItemName.text = data.title
            binding.tvItemPrice.text =
                itemView.context.getString(R.string.product_price, data.price.toString())
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(getItem(bindingAdapterPosition))
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Product> =
            object : DiffUtil.ItemCallback<Product>() {
                override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                    return oldItem == newItem
                }
            }
    }
}