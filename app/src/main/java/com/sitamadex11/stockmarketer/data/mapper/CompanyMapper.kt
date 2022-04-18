package com.sitamadex11.stockmarketer.data.mapper

import com.sitamadex11.stockmarketer.data.local.CompanyListingEntity
import com.sitamadex11.stockmarketer.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

    fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
        return CompanyListingEntity(
            name = name,
            symbol = symbol,
            exchange = exchange
        )
    }
