package com.example.finaleproject.repo

import com.example.finaleproject.util.ProjectProfileRemoteDataSource
import javax.inject.Inject

class DetailedCryptoRepo @Inject constructor(
        private val remoteDataSource: ProjectProfileRemoteDataSource
    ) {

        suspend fun historicalPrice(symbol: String, targetCur: String = "usd") =
            remoteDataSource.historicalPrice(symbol, targetCur)
}