package com.mahnoosh.dashboard.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import coil.imageLoader
import coil.request.ImageRequest
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.core.data.models.local.Product
import com.mahnoosh.dashboard.R
import com.mahnoosh.dashboard.databinding.FragmentCategoryProductsBinding
import com.mahnoosh.dashboard.databinding.FragmentDetailsBinding
import com.mahnoosh.dashboard.presentation.DashboardViewModel
import com.mahnoosh.dashboard.presentation.cat_products.CategoryProductsState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : BaseFragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<DetailsViewModel>()

    private val args: DetailsFragmentArgs by navArgs()

    private var productId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {
        productId = args.productId
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.detailsIntent.send(DetailsIntent.GetProduct(id = productId))
        }
    }

    override fun setupCollectors() {
        viewModel.state.observe(viewLifecycleOwner) { detailsState ->
            when (detailsState) {
                is DetailsState.Error -> {}
                is DetailsState.Loading -> {
                    showLoader()
                }
                is DetailsState.GetProduct -> {
                    populateProduct(product = detailsState.product)
                }
            }
        }
    }

    private fun populateProduct(product: Product?) {
        product?.let {
            with(binding) {
                val imageLoader = binding.root.context.imageLoader
                val request =
                    ImageRequest.Builder(binding.root.context).data(product?.images?.get(0))
                        .target(onSuccess = { drawable ->
                            hideLoader()
                            productImage.setImageDrawable(drawable)
                        }).listener(
                            onStart = { showLoader() },
                        ).build()
                imageLoader.enqueue(request)
                productTitle.text = product.title
                productDescription.text = product.description
            }
        }
    }

    private fun showLoader() {
        binding.apply {
            loading.visibility = View.VISIBLE
            content.visibility = View.GONE
        }
    }

    private fun hideLoader() {
        binding.apply {
            loading.visibility = View.GONE
            content.visibility = View.VISIBLE
        }
    }

    override fun setupListeners() {
        /*NO_OP*/
    }
}