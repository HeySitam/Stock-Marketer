package com.sitamadex11.stockmarketer.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListing(
        companyListingEntries: List<CompanyListingEntity>
    )

    @Query("delete from companylistingentity")
    suspend fun clearCompanyListing()

    @Query(
        """
            select * from companylistingentity
            where Lower(name) like '%' || Lower(:query) || '%' OR
            upper(:query) == symbol
        """
    )
    suspend fun searchCompanyListing(query:String) : List<CompanyListingEntity>
}