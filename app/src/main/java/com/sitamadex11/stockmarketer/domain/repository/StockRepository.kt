package com.sitamadex11.stockmarketer.domain.repository

import com.sitamadex11.stockmarketer.domain.model.CompanyListing
import com.sitamadex11.stockmarketer.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {
    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>
}