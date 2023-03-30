package com.mahnoosh.dashboard.presentation.cat_products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.dashboard.databinding.FragmentCategoryProductsBinding
import com.mahnoosh.dashboard.presentation.LIMIT
import com.mahnoosh.dashboard.presentation.cat_products.adapter.CategoryProductsPagingAdapter
import com.mahnoosh.dashboard.presentation.cat_products.adapter.LoadingStateAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.properties.Delegates

class CategoryProductsFragment : BaseFragment() {

    private var _binding: FragmentCategoryProductsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<CategoryProductsViewModel>()

    val args: CategoryProductsFragmentArgs by navArgs()

    private var catId by Delegates.notNull<Int>()

    private val categoryProductsPagingAdapter by lazy {
        CategoryProductsPagingAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {
        catId = args.CatId
        binding.recyclerview.adapter = categoryProductsPagingAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter(categoryProductsPagingAdapter::retry)
        )

        lifecycleScope.launch {
            viewModel.catProductsIntent.send(
                CategoryProductsIntent.GetCategoryProducts(
                    id = catId,
                    limit = LIMIT,
                    offset = 1
                )
            )
        }
    }

    override fun setupCollectors() {
        dgdfgdfgdfg
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is CategoryProductsState.Products -> {
                        with(binding) {
                            shimmerViewContainer.stopShimmerAnimation()
                            shimmerViewContainer.visibility = View.GONE
                            recyclerview.visibility = View.VISIBLE
                        }
                        categoryProductsPagingAdapter.submitData(lifecycle, it.products)
                    }
                    else -> {}
                }
            }
        }
    }

    override fun setupListeners() {
        /*NO_OP*/
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerViewContainer.startShimmerAnimation()
    }

    override fun onPause() {
        binding.shimmerViewContainer.stopShimmerAnimation()
        super.onPause()
    }
}