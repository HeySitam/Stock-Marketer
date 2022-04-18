package com.sitamadex11.stockmarketer.presentation.company_listings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sitamadex11.stockmarketer.domain.repository.StockRepository
import com.sitamadex11.stockmarketer.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {
    val state = mutableStateOf(CompanyListingsState())
    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.Refresh -> {

            }
            is CompanyListingsEvent.OnSearchQueryChange -> {

            }
        }
    }

    fun getCompanyListings(
        query: String = state.value.searchQuery,
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
           repository.getCompanyListings(fetchFromRemote,query)
               .collect { result ->
                   when(result){
                       is Resource.Success -> {

                       }
                       is Resource.Error -> {

                       }
                       is Resource.Loading -> {

                       }
                   }
               }
        }
    }
}