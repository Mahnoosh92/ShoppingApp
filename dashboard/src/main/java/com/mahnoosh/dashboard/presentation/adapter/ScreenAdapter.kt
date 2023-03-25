package com.mahnoosh.dashboard.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mahnoosh.dashboard.data.models.local.Category
import com.mahnoosh.dashboard.presentation.click.ClickListener
import com.mahnoosh.dashboard.presentation.screen.CategoryScreenFragment

class ScreenAdapter(
    fragment: Fragment,
    private val num: Int,
    private val clickListener: ClickListener
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = num

    override fun createFragment(position: Int) =
        CategoryScreenFragment.getInstance(position, clickListener)

}