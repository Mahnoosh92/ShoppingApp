package com.mahnoosh.dashboard.presentation

sealed class DashboardIntent {
    object GetCategories : DashboardIntent()
    object SignOut : DashboardIntent()
}
