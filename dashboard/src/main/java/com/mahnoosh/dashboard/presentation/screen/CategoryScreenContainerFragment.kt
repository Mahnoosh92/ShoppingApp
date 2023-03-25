package com.mahnoosh.dashboard.presentation.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.dashboard.databinding.FragmentCategoryScreenContainerBinding
import com.mahnoosh.dashboard.presentation.DashboardIntent
import com.mahnoosh.dashboard.presentation.DashboardState
import com.mahnoosh.dashboard.presentation.DashboardViewModel
import com.mahnoosh.dashboard.presentation.adapter.ScreenAdapter
import com.mahnoosh.dashboard.presentation.click.ClickListener
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class CategoryScreenContainerFragment : BaseFragment(), ClickListener {

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
        showLoader()
        viewModel.state.flowWithLifecycle(this.lifecycle).onEach { dashboardState ->
            when (dashboardState) {
                is DashboardState.Loading -> {}
                is DashboardState.Categories -> {
                    hideLoader()
                    viewPager.adapter = ScreenAdapter(this, dashboardState.categories.size, this)
                    TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                    }.attach()
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
            pager.visibility = View.GONE
        }
    }

    private fun hideLoader() {
        binding.apply {
            loading.visibility = View.GONE
            pager.visibility = View.VISIBLE
        }
    }

    override fun onClickCategory(catId: Int) {
        findNavController().navigate(
            CategoryScreenContainerFragmentDirections.actionCategoryScreenContainerFragmentToCategoryProductsFragment(
                CatId = catId
            )
        )
    }
}