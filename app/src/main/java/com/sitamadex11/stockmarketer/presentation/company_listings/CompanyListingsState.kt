package com.sitamadex11.stockmarketer.presentation.company_listings

import com.sitamadex11.stockmarketer.domain.model.CompanyListing

data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)
