package com.example.finaleproject.ui.detailedcrypto

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.repo.DetailedCryptoRepo
import com.example.finaleproject.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoFragmentViewModel @Inject constructor(private val repository: DetailedCryptoRepo):ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    protected val _toastError = MutableLiveData<String>()
    val toastError: LiveData<String> = _toastError

    private val _dataError = MutableLiveData<Boolean>()
    val dataError: LiveData<Boolean> = _dataError



    private val _historicalData = MutableLiveData<List<DoubleArray>>()
    val historicalData: LiveData<List<DoubleArray>> = _historicalData

    fun historicalData(symbol: String?) {
        symbol?.let {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)
                val result = repository.historicalPrice(it)
                _isLoading.postValue(false)

                when (result) {
                    is Result.Success -> {
                        _historicalData.postValue(result.data.prices)
                        _dataError.postValue(false)
                    }
                    is Result.Error-> _dataError.postValue(true)
                }
            }
        }
    }
}