package com.example.finaleproject.ui.auth.auth.register

import androidx.lifecycle.ViewModel
import com.example.finaleproject.repo.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: DatabaseRepository):ViewModel() {
    fun addMoney(){
        repository.addDefaultMoney()
    }
}