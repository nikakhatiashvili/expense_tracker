package com.example.finaleproject.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.exchange.CommercialRates
import com.example.finaleproject.model.exchange.Converter
import com.example.finaleproject.model.exchange.OfficialRatesItem
import com.example.finaleproject.repo.ExchangeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(private val repository : ExchangeRepository) : ViewModel() {

    private var counter = 0
    private var _characters = MutableLiveData<List<CommercialRates>>()
    private var _official = MutableLiveData<List<OfficialRatesItem>>()
    private var allPlayList = mutableListOf<CommercialRates>()
    val characters: LiveData<List<CommercialRates>> get() = _characters
    val official: LiveData<List<OfficialRatesItem>> get() = _official

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _money = MutableLiveData<Double>()
    val money: LiveData<Double> get() = _money

    fun load(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val data = withContext(Dispatchers.IO){
                repository.getData()
            }
            _isLoading.postValue(false)

            val officialData = withContext(Dispatchers.IO){
                repository.getOfficialData()
            }
            _official.postValue(officialData)
            _characters.postValue(data)
        }
    }
    fun convertValue(amount: Double,from:String,to:String){
       viewModelScope.launch {
            val data = withContext(Dispatchers.IO){
                repository.convertedValue(amount,from,to)
            }
            _money.postValue(data.value!!)
        }

    }

}