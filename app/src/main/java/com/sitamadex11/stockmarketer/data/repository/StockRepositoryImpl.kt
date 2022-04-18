package com.sitamadex11.stockmarketer.data.repository

import com.sitamadex11.stockmarketer.data.csv.CSVParser
import com.sitamadex11.stockmarketer.data.csv.CompanyListingParser
import com.sitamadex11.stockmarketer.data.local.StockDatabase
import com.sitamadex11.stockmarketer.data.mapper.toCompanyListing
import com.sitamadex11.stockmarketer.data.mapper.toCompanyListingEntity
import com.sitamadex11.stockmarketer.data.remote.StockApi
import com.sitamadex11.stockmarketer.domain.model.CompanyListing
import com.sitamadex11.stockmarketer.domain.repository.StockRepository
import com.sitamadex11.stockmarketer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    val api: StockApi,
    val db: StockDatabase,
    val companyListingParser: CSVParser<CompanyListing>
): StockRepository
{
    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))

            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

            if(shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("OOPs! Can't load.."))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("OOPs! Can't load.."))
                null
            }

            remoteListings?.let { listings ->
                dao.clearCompanyListing()
                dao.insertCompanyListing(
                    listings.map {
                        it.toCompanyListingEntity()
                    }
                )
                emit(Resource.Success(
                    data = dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }

}