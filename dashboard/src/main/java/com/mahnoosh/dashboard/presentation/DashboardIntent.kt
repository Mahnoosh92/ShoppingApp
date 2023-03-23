package com.mahnoosh.dashboard.presentation

import com.mahnoosh.dashboard.data.models.local.Category

sealed class DashboardIntent {
    object GetCategories : DashboardIntent()
}
