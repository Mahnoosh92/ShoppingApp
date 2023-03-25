package com.mahnoosh.dashboard.presentation

import com.mahnoosh.dashboard.data.models.local.Category

sealed class DashboardState {
    object Loading : DashboardState()
    object SignOut : DashboardState()
    data class Categories(val categories: List<Category>) : DashboardState()
    data class Error(val error: String) : DashboardState()
}
