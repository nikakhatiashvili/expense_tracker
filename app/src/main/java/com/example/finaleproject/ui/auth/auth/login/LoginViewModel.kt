package com.example.finaleproject.ui.auth.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.repo.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: DatabaseRepository):ViewModel() {

    private var _loggedIn = MutableLiveData<Boolean>()
    val loggedIn: LiveData<Boolean> get() = _loggedIn

    fun login(string: String,string1: String){
       viewModelScope.launch(Dispatchers.IO) {
           val data = repository.login()
           var result =data.signInWithEmailAndPassword(string,string1).addOnCompleteListener {
               if (it.isSuccessful){
                   _loggedIn.postValue(true)
               }else{
                   _loggedIn.postValue(false)
               }
           }
       }

    }
}