package com.example.finaleproject.ui.auth.auth.register

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
class RegisterViewModel @Inject constructor(private val repository: DatabaseRepository) :
    ViewModel() {

    val registerFlow = MutableSharedFlow<Resource<Boolean>>()
    fun addMoney() {
        repository.addDefaultMoney()
    }

    fun register(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.register().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewModelScope.launch {
                            registerFlow.emit(Resource.Success(true))
                            repository.signOut().signOut()
                        }
                    } else {
                        viewModelScope.launch {
                            registerFlow.emit(Resource.Error(it.exception?.message.toString()))
                        }
                    }
                }
        }
    }
}

