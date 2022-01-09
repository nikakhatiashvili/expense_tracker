package com.example.finaleproject.ui.expense.income

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.transaction.Transaction
import com.example.finaleproject.repo.DatabaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PayingViewModel @Inject constructor(private val repository : DatabaseRepository) : ViewModel() {

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    private var _money = MutableLiveData<String>()
    val money: LiveData<String> get() = _money
    fun saveTransaction(transaction: Transaction){
        _isLoading.postValue(repository.saveTransaction(transaction))
    }

    fun readMoney(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.readMoney().child("money").ref
            data.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val money = snapshot.getValue()
                    _money.postValue(money.toString())
                    Log.d("money", money.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
        }
    }

    fun changeMoney(amount: String, money: String?) {
        repository.changeMoney(amount,money)
    }

    fun increaseMoney(amount: String, money: String?) {
        repository.increaseMoney(amount,money)
    }

}