package com.example.finaleproject.repo

import androidx.lifecycle.MutableLiveData
import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.network.CryptoNetwork
import javax.inject.Inject

class CryptoRepository@Inject constructor(private val homeNetwork: CryptoNetwork) {

    suspend fun getData():List<CryptoItem>{
        return  homeNetwork.getCoinList().body()!!
    }
}