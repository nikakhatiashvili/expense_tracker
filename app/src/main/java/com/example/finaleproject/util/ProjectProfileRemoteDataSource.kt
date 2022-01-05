package com.example.finaleproject.util

import com.example.finaleproject.model.detailed.HistoricalPriceResponse
import com.example.finaleproject.network.CryptoNetwork
import javax.inject.Inject

class ProjectProfileRemoteDataSource @Inject constructor(private val service: CryptoNetwork) : BaseRemoteDataSource(){

    //fetch historical price from backend
    suspend fun historicalPrice(symbol: String, targetCurrency: String, days: Int = 30): Result<HistoricalPriceResponse> =
        getResult { service.historicalPrice(symbol, targetCurrency, days) }

}