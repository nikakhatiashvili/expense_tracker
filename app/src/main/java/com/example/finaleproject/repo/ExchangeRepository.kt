package com.example.finaleproject.repo

import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.model.exchange.CommercialRates
import com.example.finaleproject.model.exchange.Converter
import com.example.finaleproject.model.exchange.OfficialRatesItem
import com.example.finaleproject.network.CryptoNetwork
import javax.inject.Inject

class ExchangeRepository @Inject constructor(private val apiInterface:CryptoNetwork) {
    suspend fun getData():List<CommercialRates>{
        return  apiInterface.getExchange().body()?.commercialRatesList!!
    }
    suspend fun getOfficialData():List<OfficialRatesItem>{
        return apiInterface.getOfficialExchange().body()!!
    }

    suspend fun convertedValue(amount:Double,from:String,to:String):Converter{
        return apiInterface.getConvertedValue(amount, from, to).body()!!
    }

}