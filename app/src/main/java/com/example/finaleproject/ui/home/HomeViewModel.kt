package com.example.finaleproject.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.model.transaction.Transaction
import com.example.finaleproject.repo.DatabaseRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: DatabaseRepository) : ViewModel() {

    private var _crypto = MutableLiveData<List<Transaction>>()
    val crypto: LiveData<List<Transaction>> get() = _crypto
    var listRes: MutableList<Transaction> = ArrayList()

    fun readTransactions(){

    }

    fun getTransactions(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTransaction().addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val transaction = snapshot.getValue(Transaction::class.java)
                    listRes.add(transaction as Transaction)
                    _crypto.postValue(listRes)
                }
                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }
                override fun onChildRemoved(snapshot: DataSnapshot) {
                }
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
    fun getMoney(){

    }

}