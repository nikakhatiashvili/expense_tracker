package com.example.finaleproject.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.di.ApiService
import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.repo.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val repository: CryptoRepository) :
    ViewModel() {

    private var _crypto = MutableLiveData<List<CryptoItem>>()
    val crypto: LiveData<List<CryptoItem>> get() = _crypto


    fun getCrypto(){
        viewModelScope.launch {
            val data = withContext(Dispatchers.IO){
                repository.getData()
            }
            _crypto.postValue(data)
        }
    }
}

