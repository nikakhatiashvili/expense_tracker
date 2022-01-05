package com.example.finaleproject.util

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()

    override fun toString(): String {
        return when(this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[message=$message]"
            is Loading -> "Loading"
        }
    }
}

val Result<*>.succeeded
    get() = this is Result.Success && data != null