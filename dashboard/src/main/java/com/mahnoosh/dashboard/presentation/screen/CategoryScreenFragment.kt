package com.mahnoosh.dashboard.presentation.screen

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.ImageLoader
import coil.imageLoader
import coil.load
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.android.material.tabs.TabLayoutMediator
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.dashboard.R
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.dashboard.databinding.FragmentCategoryScreenBinding
import com.mahnoosh.dashboard.presentation.DashboardState
import com.mahnoosh.dashboard.presentation.DashboardViewModel
import com.mahnoosh.dashboard.presentation.adapter.ScreenAdapter
import com.mahnoosh.dashboard.presentation.click.ClickListener
import com.mahnoosh.utils.extensions.clicks
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel

private const val ARG_POSITION = "ARG_POSITION_CATEGORY_SCREEN"

class CategoryScreenFragment : BaseFragment() {

    private var _binding: FragmentCategoryScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<DashboardViewModel>()

    private var allCats: List<Category>? = null
    private var position: Int = 1

    var clickListener: ClickListener? = null

    companion object {
        fun getInstance(position: Int, clickListener: ClickListener) =
            CategoryScreenFragment().apply {
                arguments = bundleOf(ARG_POSITION to position)
                onClick(clickListener = clickListener)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {
        /*NO_OP*/
    }

    override fun setupCollectors() {
        showLoader()
        viewModel.state.flowWithLifecycle(this.lifecycle).onEach { dashboardState ->
                when (dashboardState) {
                    is DashboardState.Loading -> {}
                    is DashboardState.Categories -> {
                        allCats = dashboardState.categories
                        hideLoader()
                        populateData()
                    }
                    is DashboardState.Error -> {}
                    else -> {}
                }

            }.launchIn(this.lifecycleScope)
    }

    override fun setupListeners() {
        /*NO_OP*/
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

    private fun populateData() {
        position = requireArguments().getInt(ARG_POSITION)
        if (allCats != null) {
            val data = allCats?.get(position)
            binding.apply {
                val imageLoader = binding.root.context.imageLoader
                val request = ImageRequest.Builder(binding.root.context).data(data?.image)
                    .target(onSuccess = { drawable ->
                        hideLoader()
                        categoryImage.setImageDrawable(drawable)
                    }).listener(
                        onStart = { showLoader() },
                    ).build()
                imageLoader.enqueue(request)
//                categoryImage.load(data?.image)
                categoryText.text = data?.name
            }
            lifecycleScope.launch {
                viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    binding.root.clicks().debounce(500L).collect {
                            clickListener?.onClickCategory(catId = data?.id ?: 0)
                        }
                }
            }
        }
    }

    fun onClick(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

}