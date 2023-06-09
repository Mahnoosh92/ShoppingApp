package com.mahnoosh.dashboard.presentation.cat_products.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.databinding.ItemCatProductBinding

class CategoryProductsPagingAdapter(private val onClick: (Int?) -> Unit) :
    PagingDataAdapter<Product, CategoryProductsPagingAdapter.ViewHolder>(DiffUtilCallBack) {

    private lateinit var binding: ItemCatProductBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCatProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemCatProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
            with(binding) {
                catPrdImg.load(item.images?.get(0))
                catPrdPrice.text = item.price.toString()
                catPrdTitle.text = item.title.toString()
                catPrdDesc.text = item.description
                catPrdCreationAt.text = item.creationAt
                root.setOnClickListener {
                    onClick(item.id)
                }
            }
        }
    }

    object DiffUtilCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Product,
            newItem: Product
        ): Boolean {
            return oldItem == newItem
        }
    }
}