package com.mahnoosh.product.presentation.product.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.product.data.datasource.remote.product.adapter.ProductListAdapterListAdapter
import com.mahnoosh.product.databinding.FragmentProductListBinding
import com.mahnoosh.utils.extensions.shortSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListFragment : BaseFragment() {

    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ProductListViewModel>()

    private lateinit var adapter: ProductListAdapterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {
        adapter = ProductListAdapterListAdapter()
        binding.recycleView.adapter = adapter
        lifecycleScope.launch {
            viewModel.productListIntent.send(ProductListIntent.Products)
        }
    }

    override fun setupCollectors() {
        viewModel.state.observe(viewLifecycleOwner) { productListState ->
            when (productListState) {
                is ProductListState.Loading -> {}
                is ProductListState.Products -> {
                    adapter.submitList(productListState.products)
                }
                is ProductListState.Error -> {
                    binding.root.shortSnackBar(productListState.error)
                }
            }
        }
    }

    override fun setupListeners() {
        /*NO_OP*/
    }
}