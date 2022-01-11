package com.example.finaleproject.repo

import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.network.CryptoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoRepository@Inject constructor(private val homeNetwork: CryptoNetwork) {


    fun getCoins(): Flow<List<CryptoItem>> = flow {
        emit(homeNetwork.getCoinList().body()!!)
    }.flowOn(Dispatchers.IO)
}