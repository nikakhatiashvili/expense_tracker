package com.example.finaleproject.repo

import com.example.finaleproject.network.CryptoNetwork
import javax.inject.Inject

class DetailedCryptoRepo @Inject constructor(
    private val service: CryptoNetwork
    ) {

        suspend fun historicalPrice(symbol: String, targetCur: String = "usd",days: Int = 30) =
            service.historicalPrice(symbol, targetCur,days)
}