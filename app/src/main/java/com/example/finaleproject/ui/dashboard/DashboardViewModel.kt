package com.example.finaleproject.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.exchange.CommercialRates
import com.example.finaleproject.repo.ExchangeRepository
import com.example.finaleproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository : ExchangeRepository) : ViewModel() {





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
       viewModelScope.launch {
            val data = withContext(Dispatchers.IO){
                repository.convertedValue(amount,from,to)
            }
           delay(200)
           moneys.emit(data.value!!)
        }

    }
}
