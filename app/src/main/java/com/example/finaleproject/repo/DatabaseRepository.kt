package com.example.finaleproject.repo

import com.example.finaleproject.model.transaction.Transaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import javax.inject.Inject




class DatabaseRepository @Inject constructor(private val databaseReference: DatabaseReference,private val firebaseAuth: FirebaseAuth) {

    lateinit var  transaction :Any
    lateinit var transactions:Transaction
    var done = false
    fun login():FirebaseAuth{
     return firebaseAuth

    }
    fun getTransaction(): DatabaseReference {
        return   databaseReference.child(firebaseAuth.uid.toString()).child("transactions").ref
    }
    fun readMoney(): DatabaseReference {
        return   databaseReference.child(firebaseAuth.uid.toString()).ref
    }
    fun addDefaultMoney(){
        databaseReference.child(firebaseAuth.uid.toString()).child("money").setValue("0")
    }
    fun register():FirebaseAuth{
        return  firebaseAuth


    }
    fun signOut():FirebaseAuth{
        return  firebaseAuth
    }
    fun resetPass(email:String){
        firebaseAuth.sendPasswordResetEmail(email)
    }


    fun changeMoney(amount: String, money: String?){
        val newMoney = money?.toInt()?.minus(amount.toInt()).toString()
        databaseReference.child(firebaseAuth.uid.toString()).child("money").setValue(newMoney)
    }
    fun increaseMoney(amount: String, money: String?){
        val newMoney = money?.toInt()?.plus(amount.toInt())
        databaseReference.child(firebaseAuth.uid.toString()).child("money").setValue(newMoney)
    }


    var listRes: MutableList<Transaction> = ArrayList()
    fun saveTransaction(transaction:Transaction):Boolean{
        val databaseRef = databaseReference.child(firebaseAuth.uid.toString()).child("transactions")
        databaseRef.push().setValue(transaction)
        return  true
    }

}
