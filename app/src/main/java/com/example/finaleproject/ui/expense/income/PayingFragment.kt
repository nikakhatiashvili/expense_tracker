package com.example.finaleproject.ui.expense.income

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentDashboardBinding
import com.example.finaleproject.databinding.FragmentPayingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Transaction
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PayingFragment : Fragment() {


    private var _binding: FragmentPayingBinding? = null
    private val binding get() = _binding!!
    val categoryes:String = "Income"
    var  selectedIncome:String?  = null
    var  selectedExpense:String?  = null
    lateinit var firstValue:String
    lateinit var secondValue:String

    @Inject
    lateinit var DatabaseReference:DatabaseReference

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPayingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }

    private fun bind(){
        with(binding){
            val category = ArrayAdapter(requireContext(),R.layout.second_textview,resources.getStringArray(R.array.category))

            val expense = ArrayAdapter(requireContext(),R.layout.second_textview,resources.getStringArray(R.array.expense))
            val income = ArrayAdapter(requireContext(),R.layout.second_textview,resources.getStringArray(R.array.income))
            firstValue = binding.dropdownCategory.text.toString()
            secondValue = binding.dropdownExpense.text.toString()
            dropdownCategory.setAdapter(category)

            dropdownExpense.isClickable = false
            dropdownCategory.onItemClickListener = AdapterView.OnItemClickListener{ adapterView, view, i, l ->
                val selected = category.getItem(i)
                firstValue = selected.toString()
                if (firstValue == categoryes){
                    dropdownExpense.isClickable = true
                    constraintLayout.setBackgroundColor(resources.getColor(R.color.light_green))
                    howMuch.setTextColor(resources.getColor(R.color.text_green))
                    dropdownExpense.setAdapter(income)
                }else{
                    dropdownExpense.setAdapter(expense)
                    constraintLayout.setBackgroundColor(resources.getColor(R.color.light_red))
                    howMuch.setTextColor(resources.getColor(R.color.text_RED))
                }
            }
            dropdownExpense.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
                if (firstValue == categoryes){
                     selectedIncome = income.getItem(i)
                }else{
                     selectedExpense = expense.getItem(i)
                }
            }
        }
        setListeners()
    }
    private fun setListeners(){
        binding.continueBtn.setOnClickListener {
            val amount = binding.amountEt.text.toString()
            val description = binding.description.editText?.text.toString()
            if (!amount.isNullOrEmpty() && !description.isNullOrEmpty()){
//                setFirebase(amount.toDouble(),firstValue,secondValue,description)
            }

        }
    }

//    private fun setFirebase(amount:Double,category:String,transactionCategory:String,description:String){
//        val transaction = com.example.finaleproject.model.transaction.Transaction(amount,category,transactionCategory,description)
//        val databaseRef = databaseReference.child(firebaseAuth.currentUser?.uid!!).ref
//        databaseRef.setValue(transaction).addOnSuccessListener {
//            findNavController().navigate(R.id.action_payingFragment2_to_bottomFragment)
//        }.addOnFailureListener {
//            Toast.makeText(requireContext(),it.message.toString(),Toast.LENGTH_SHORT).show()
//        }
//    }

}