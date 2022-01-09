package com.example.finaleproject.ui.home

import android.util.Log.d
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
class HomeViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    private var _crypto = MutableLiveData<List<Transaction>>()
    val crypto: LiveData<List<Transaction>> get() = _crypto

    private var _money = MutableLiveData<String>()
    val money: LiveData<String> get() = _money

    private var _income = MutableLiveData<String>()
    val income: LiveData<String> get() = _income

    private var _expense = MutableLiveData<String>()
    val expense: LiveData<String> get() = _expense

    private var _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> get() = _loggedIn


    var listRes: MutableList<Transaction> = ArrayList()
    val mLiveNewTransaction = MutableLiveData<Transaction>()
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
                        _crypto.postValue(queryList)
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
                    _money.postValue(money.toString())
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

    fun signOut(){

       val data = repository.signOut().signOut()
        val user = repository.signOut().currentUser?.email
        if (user != null){
            _loggedIn.postValue(true)
        }else{
            _loggedIn.postValue(false)
        }
    }
}