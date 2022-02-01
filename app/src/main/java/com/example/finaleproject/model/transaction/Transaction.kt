package com.example.finaleproject.model.transaction

data class Transaction(
    val amount:Double?= null,
    val category:String?= null,
    val description:String?= "",
    val transaction_Category:String?= null,
    val time:String?= null
)
