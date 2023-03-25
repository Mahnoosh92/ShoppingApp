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

    private val cryptoListAdapter by lazy {
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
        binding.recyclerview.adapter = cryptoListAdapter
        lifecycleScope.launch {
            viewModel.catProductsIntent.send(
                CategoryProductsIntent.GetCategoryProducts(
                    id = catId,
                    limit = LIMIT,
                    offset = LIMIT
                )
            )
        }
    }

    override fun setupCollectors() {
        viewLifecycleOwner.lifecycleScope.launch {
            delay(2500L)
            viewModel.state.observe(viewLifecycleOwner) {
                when (it) {
                    is CategoryProductsState.Products -> {
                        with(binding) {
                            shimmerViewContainer.stopShimmerAnimation();
                            shimmerViewContainer.setVisibility(View.GONE);
                            recyclerview.visibility = View.VISIBLE
                        }
                        cryptoListAdapter.submitData(lifecycle, it.products)
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