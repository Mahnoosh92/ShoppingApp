package com.mahnoosh.product.data.datasource.remote.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.product.R
import com.mahnoosh.product.databinding.CatProductItemBinding


class ProductListAdapterListAdapter :
    ListAdapter<Product, ProductListAdapterListAdapter.ItemViewholder>(DiffCallback()) {

    private lateinit var binding: CatProductItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        binding = CatProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewholder(binding.root)
    }

    override fun onBindViewHolder(
        holder: ProductListAdapterListAdapter.ItemViewholder, position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class ItemViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Product) {
            binding.apply {
                catPrdImg.load(item.images?.get(0))
                catPrdPrice.text = item.price.toString()
                catPrdTitle.text = item.title.toString()
                catPrdDesc.text = item.description
                catPrdCreationAt.text = item.creationAt
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem?.id == newItem?.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}