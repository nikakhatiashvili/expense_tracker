package com.example.finaleproject.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.repo.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val repository: CryptoRepository) :
    ViewModel() {




    private val _cryptoFlow: MutableStateFlow<ApiState>
            = MutableStateFlow(ApiState.Empty)

    val cryptoFlow: StateFlow<ApiState> = _cryptoFlow

    fun getCrypto(){
        viewModelScope.launch(Dispatchers.IO) {
            _cryptoFlow.value = ApiState.Loading
            repository.getCoins().catch { e ->
                _cryptoFlow.value = ApiState.Failure(e)
            }.collect{
                _cryptoFlow.value = ApiState.Success(it)
            }
        }
    }
}

sealed class ApiState{
    object Loading : ApiState()
    class Failure(val msg:Throwable) : ApiState()
    class Success(val data: List<CryptoItem>) : ApiState()
    object Empty : ApiState()
}