package com.mahnoosh.dashboard.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import coil.load
import com.google.android.material.tabs.TabLayoutMediator
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.dashboard.R
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.dashboard.databinding.FragmentCategoryScreenBinding
import com.mahnoosh.dashboard.presentation.DashboardState
import com.mahnoosh.dashboard.presentation.DashboardViewModel
import com.mahnoosh.dashboard.presentation.adapter.ScreenAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.activityViewModel

private const val ARG_POSITION = "ARG_POSITION_CATEGORY_SCREEN"

class CategoryScreenFragment : BaseFragment() {

    private var _binding: FragmentCategoryScreenBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModel<DashboardViewModel>()

    private var allCats: List<Category>? = null
    private var position: Int = 1

    companion object {
        fun getInstance(position: Int) = CategoryScreenFragment().apply {
            arguments = bundleOf(ARG_POSITION to position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {

    }

    override fun setupCollectors() {
        viewModel.state
            .flowWithLifecycle(this.lifecycle)
            .onEach { dashboardState ->
                when (dashboardState) {
                    is DashboardState.Loading -> {}
                    is DashboardState.Categories -> {
                        allCats = dashboardState.categories
                        populateData()
                    }
                    is DashboardState.Error -> {}
                }

            }.launchIn(this.lifecycleScope)
    }

    override fun setupListeners() {
        /*NO_OP*/
    }

    fun populateData() {
        position = requireArguments().getInt(ARG_POSITION)
        if (allCats != null) {
            val data = allCats?.get(position)
            binding.apply {
                categoryImage.load(data?.image)
                categoryText.text = data?.name
            }
        }
    }

}