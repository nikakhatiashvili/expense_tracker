package com.example.finaleproject.ui.expense.income

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.finaleproject.model.transaction.Transaction
import com.example.finaleproject.repo.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PayingViewModel @Inject constructor(private val repository : DatabaseRepository) : ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun saveTransaction(transaction: Transaction){
        _isLoading.postValue(repository.saveTransaction(transaction))
    }

}