package com.example.finaleproject.ui.expense.income

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentPayingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PayingFragment : Fragment() {


    private var _binding: FragmentPayingBinding? = null
    private val binding get() = _binding!!
    val categoryes:String = "Income"
    var  selectedIncome:String?  = null
    var  selectedExpense:String?  = null
    var firstValue = "Income"
    var secondValue = "Food"
    var thirdValue = "Salary"

    private val viewModel:PayingViewModel by viewModels()
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
    private fun checkSpinner(){
        if (firstValue == "Income"){
            binding.spinnerCategoryExpense.visibility = View.GONE
            binding.spinnerCategoryIncome.visibility = View.VISIBLE
        }else{
            binding.spinnerCategoryIncome.visibility = View.GONE
            binding.spinnerCategoryExpense.visibility = View.VISIBLE
        }
    }

    private fun bind(){
        val expense = resources.getStringArray(R.array.expense)
        val expenses = ArrayAdapter(requireContext(),R.layout.second_textview,expense)
        val category = resources.getStringArray(R.array.category)
        val Categorys = ArrayAdapter(requireContext(),R.layout.second_textview,category)
        val income = resources.getStringArray(R.array.income)
        val incomes = ArrayAdapter(requireContext(),R.layout.second_textview,income)
            with(binding){
                spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val selected = p0?.getItemAtPosition(p2)
                        firstValue = selected.toString()
                        checkSpinner()
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {

                    }
                }
                spinnerCategoryExpense.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val selected = p0?.getItemAtPosition(p2).toString()
                        thirdValue = selected
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
                spinnerCategoryIncome.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                        val selected = p0?.getItemAtPosition(p2).toString()
                        secondValue = selected
                    }
                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }
                }
            }
//        with(binding){
//            val category = ArrayAdapter(requireContext(),R.layout.second_textview,resources.getStringArray(R.array.category))
//            val expense = ArrayAdapter(requireContext(),R.layout.second_textview,resources.getStringArray(R.array.expense))
//            val income = ArrayAdapter(requireContext(),R.layout.second_textview,resources.getStringArray(R.array.income))
//            firstValue = binding.dropdownCategory.text.toString()
//            secondValue = binding.dropdownExpense.text.toString()
//            dropdownCategory.setAdapter(category)
//
//            dropdownExpense.isClickable = false
//            dropdownCategory.onItemClickListener = AdapterView.OnItemClickListener{ adapterView, view, i, l ->
//                val selected = category.getItem(i)
//                firstValue = selected.toString()
//                if (firstValue == categoryes){
//                    dropdownExpense.isClickable = true
//                    howMuch.setTextColor(resources.getColor(R.color.text_green))
//                    dropdownExpense.setAdapter(income)
//                }else{
//                    dropdownExpense.setAdapter(expense)
//                    howMuch.setTextColor(resources.getColor(R.color.text_RED))
//                }
//            }
//            dropdownExpense.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, i, l ->
//                selectedIncome = income.getItem(i)
//                secondValue = selectedIncome!!
//            }
//        }
        setListeners()
    }
    private fun setListeners(){
        binding.continueBtn.setOnClickListener {
            val amount = binding.amountEt.text.toString()
            val description = binding.description.editText?.text.toString()
            if (!amount.isNullOrEmpty() && !description.isNullOrEmpty() && !secondValue.isNullOrEmpty()){
                if(binding.spinnerCategoryExpense.isVisible){
                    val transaction = com.example.finaleproject.model.transaction.Transaction(amount.toDouble(),firstValue,description,thirdValue)
                    viewModel.saveTransaction(transaction)
                }else{
                    val transaction = com.example.finaleproject.model.transaction.Transaction(amount.toDouble(),firstValue,description,secondValue)
                    viewModel.saveTransaction(transaction)
                }
            }
        }
        viewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(context,"transaction saved",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_payingFragment2_to_bottomFragment)
            }else{
                Toast.makeText(context,"transaction failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}