package com.example.finaleproject.ui.home

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.pieChartExpense
import com.example.finaleproject.model.pieChartIncome
import com.example.finaleproject.model.transaction.Transaction
import com.example.finaleproject.repo.DatabaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {


    val exchangeResponse = MutableStateFlow<List<Transaction>>(emptyList())
    val expenseResponse = MutableStateFlow<List<pieChartExpense>>(emptyList())
    val incomeResponse = MutableStateFlow<List<pieChartIncome>>(emptyList())

    val moneys  = MutableStateFlow<String>("")

    val _loggedIn  = MutableStateFlow<Boolean>(false)
    val test= 0


    suspend fun changeLogged(){
        _loggedIn.emit(false)
        exchangeResponse.emit(emptyList())
    }

    fun getTransactions(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTransaction().addValueEventListener(object :ValueEventListener{
                val queryList = mutableListOf<Transaction>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (i in snapshot.children){
                            val item = i.getValue(Transaction::class.java)
                            if (item != null){
                                queryList.add(item)
                            }
                        }
                        exchangeResponse.tryEmit(queryList)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        }

    }
    fun getExpense(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getExpense().addValueEventListener(object :ValueEventListener{
                val queryList = mutableListOf<pieChartExpense>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (i in snapshot.children){
                            val item = i.getValue(pieChartExpense::class.java)
                            if (item != null){
                                queryList.add(item)
                            }
                        }
                        expenseResponse.tryEmit(queryList)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    fun getIncome(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getIncome().addValueEventListener(object :ValueEventListener{
                val queryList = mutableListOf<pieChartIncome>()
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (i in snapshot.children){
                            val item = i.getValue(pieChartIncome::class.java)
                            if (item != null){
                                queryList.add(item)
                            }
                        }
                        incomeResponse.tryEmit(queryList)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    fun readMoney(){
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.readMoney().child("money").ref
            data.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val money = snapshot.getValue()
                    moneys.tryEmit(money.toString())
                    d("money", money.toString())
                }
                
                override fun onCancelled(error: DatabaseError) {
                }

            })
        }
    }
    fun resetPass(email:String){
        repository.resetPass(email)
    }

    suspend fun signOut(){
       val data = repository.signOut().signOut()
        val user = repository.signOut().currentUser?.email
        if (user != null){
            _loggedIn.emit(false)
        }else{
            _loggedIn.emit(true)
        }
    }
}