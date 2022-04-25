package com.example.yelpapp.data.repository


import com.example.yelpapp.data.datasource.BusinessLocalDataSource
import com.example.yelpapp.data.datasource.BusinessRemoteDataSource
import com.example.yelpapp.domain.Error
import javax.inject.Inject

class BusinessRepository @Inject constructor(
    private val regionRepository : RegionRepository,
    private val localDataSource : BusinessLocalDataSource,
    private val remoteDataSource : BusinessRemoteDataSource
) {

/*    private val DEFAULT_LATITUDE = -31.417
    private val DEFAULT_LONGITUDE = -64.183*/

    val business get() = localDataSource.business

    suspend fun requestBusiness(): Error? {
        if(localDataSource.isEmpty()){
            val business = remoteDataSource.searchBusiness(regionRepository.findLastLocation())
            business.fold(ifLeft = { return it }){
                localDataSource.save(it)
            }
        }
        return null
    }
}