package com.example.finaleproject.extensions

fun String.checkEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}