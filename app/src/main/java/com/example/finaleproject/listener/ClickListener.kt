package com.example.finaleproject.listener

import com.example.finaleproject.model.CryptoItem

fun interface ClickListener {
    fun onClick(data:CryptoItem)
}
 interface ClickListeners {
    fun onClick(data:String)
}
fun interface SelectedClickListener{
    fun onClicks(amount:Double,string: String,string1:String)
}