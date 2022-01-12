package com.example.finaleproject.ui.auth.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finaleproject.repo.DatabaseRepository
import com.example.finaleproject.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: DatabaseRepository):ViewModel() {

    val loginFlow = MutableSharedFlow<Resource<Boolean>>()

    fun login(string: String,string1: String){
       viewModelScope.launch(Dispatchers.IO) {
           val data = repository.login()

           var result =data.signInWithEmailAndPassword(string,string1).addOnCompleteListener {
               if (it.isSuccessful){
                   viewModelScope.launch {
                       loginFlow.emit(Resource.Success(true))
                   }
               }else{
                   viewModelScope.launch {
                       loginFlow.emit(Resource.Error(it.exception?.message.toString()))
                   }
               }
           }
       }

    }
}