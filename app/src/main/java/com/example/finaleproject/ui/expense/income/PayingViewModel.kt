package com.example.finaleproject.ui.expense.income

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.transaction.Transaction
import com.example.finaleproject.repo.DatabaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PayingViewModel @Inject constructor(private val repository : DatabaseRepository) : ViewModel() {

//    protected val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading

    val _isLoading  = MutableSharedFlow<Boolean>()
    val moneys  = MutableStateFlow<String>("")

    suspend fun saveTransaction(transaction: Transaction){
        _isLoading.emit(repository.saveTransaction(transaction))
    }

    fun readMoney(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.readMoney().child("money").ref
            data.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val money = snapshot.getValue()
                    moneys.tryEmit(money.toString())
                    Log.d("money", money.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    println("sd")
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