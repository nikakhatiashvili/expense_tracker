package com.example.finaleproject.repo

import com.example.finaleproject.model.exchange.CommercialRates
import com.example.finaleproject.model.exchange.Converter
import com.example.finaleproject.network.CryptoNetwork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ExchangeRepository @Inject constructor(private val apiInterface:CryptoNetwork) {

    fun getData(): Flow<List<CommercialRates>> = flow {
        emit(apiInterface.getExchange().body()?.commercialRatesList!!)
    }.flowOn(Dispatchers.IO)

    suspend fun convertedValue(amount:Double,from:String,to:String):Converter{
        return apiInterface.getConvertedValue(amount, from, to).body()!!
    }

}

