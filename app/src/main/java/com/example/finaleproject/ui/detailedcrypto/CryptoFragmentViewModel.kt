package com.example.finaleproject.ui.detailedcrypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.repo.DetailedCryptoRepo
import com.example.finaleproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoFragmentViewModel @Inject constructor(private val repository: DetailedCryptoRepo):ViewModel() {



    val cryptoHistorical = MutableSharedFlow<Resource<List<DoubleArray>>>()



    fun historicalData(symbol: String?) {
        symbol?.let {
            viewModelScope.launch(Dispatchers.IO) {
                cryptoHistorical.tryEmit(Resource.Loading())
                val result = repository.historicalPrice(it)
                if (result.isSuccessful){
                    cryptoHistorical.emit(Resource.Success(result.body()?.prices!!))
                }else{
                    cryptoHistorical.emit(Resource.Error(result.errorBody().toString()))
                }
            }
        }
    }
}