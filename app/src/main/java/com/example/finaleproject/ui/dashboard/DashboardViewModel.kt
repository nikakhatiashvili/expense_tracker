package com.example.finaleproject.ui.dashboard

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.exchange.CommercialRates
import com.example.finaleproject.repo.ExchangeRepository
import com.example.finaleproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository : ExchangeRepository) : ViewModel() {



    private val listOfCharacter = mutableListOf<Char>(',', ' ', '-')
    private var searchJob: Job? = null
    val moneys  = MutableSharedFlow<Double>()

    val exchangeResponse= MutableSharedFlow<Resource<List<CommercialRates>>>()

    fun load(){
        viewModelScope.launch {
            exchangeResponse.emit(Resource.Loading())

            repository.getData().catch { e ->
                exchangeResponse.emit(Resource.Error(e.message.toString()))
            }.collect{
                exchangeResponse.emit(Resource.Success(it))
            }
        }
    }

    fun convertValue(amount: Double,from:String,to:String){
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            delay(200)
            repository.convertedValue(amount,from,to)
            moneys.emit(repository.convertedValue(amount,from, to).value!!)
        }
    }
    fun containsError(double: String): Boolean {
        var result = false
        d("sadasda",double)
        for (i in double.indices) {
            if (listOfCharacter.contains(double[i])) {
                result = true
            }
        }
        return result
    }
}
