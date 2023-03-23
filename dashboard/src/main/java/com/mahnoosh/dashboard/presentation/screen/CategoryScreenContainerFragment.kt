package com.mahnoosh.dashboard.presentation.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.dashboard.R
import com.mahnoosh.dashboard.databinding.ActivityDashboardBinding
import com.mahnoosh.dashboard.databinding.FragmentCategoryScreenBinding
import com.mahnoosh.dashboard.databinding.FragmentCategoryScreenContainerBinding
import com.mahnoosh.dashboard.presentation.DashboardIntent
import com.mahnoosh.dashboard.presentation.DashboardState
import com.mahnoosh.dashboard.presentation.DashboardViewModel
import com.mahnoosh.dashboard.presentation.adapter.ScreenAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class CategoryScreenContainerFragment : BaseFragment() {

    private var _binding: FragmentCategoryScreenContainerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val viewModel by activityViewModel<DashboardViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCategoryScreenContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {
        viewPager = binding.pager
        tabLayout = binding.tabLayout
        lifecycleScope.launch {
            viewModel.dashboardIntent.send(DashboardIntent.GetCategories)
        }
    }

    override fun setupCollectors() {
        viewModel.state.flowWithLifecycle(this.lifecycle).onEach { dashboardState ->
            when (dashboardState) {
                is DashboardState.Loading -> {}
                is DashboardState.Categories -> {
                    viewPager.adapter = ScreenAdapter(this, dashboardState.categories.size)
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    }.attach()
                }
                is DashboardState.Error -> {}
            }

        }.launchIn(this.lifecycleScope)
    }

    override fun setupListeners() {
        /*NO_OP*/
    }
}