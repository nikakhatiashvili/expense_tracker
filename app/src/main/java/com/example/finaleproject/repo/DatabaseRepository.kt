package com.example.finaleproject.repo

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.finaleproject.model.transaction.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.w3c.dom.Comment
import javax.inject.Inject
import com.google.type.LatLng




class DatabaseRepository @Inject constructor(private val databaseReference: DatabaseReference,private val firebaseAuth: FirebaseAuth) {

    lateinit var  transaction :Any
    lateinit var transactions:Transaction
    var done = false

    fun getTransaction(): DatabaseReference {
        return   databaseReference.child(firebaseAuth.uid.toString()).child("transactions").ref

    }


    var listRes: MutableList<Transaction> = ArrayList()
    fun saveTransaction(transaction:Transaction):Boolean{
        val databaseRef = databaseReference.child(firebaseAuth.uid.toString()).child("transactions")
        databaseRef.push().setValue(transaction)
        return  true
    }

}
