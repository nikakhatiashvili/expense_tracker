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
import dagger.hilt.android.AndroidEntryPoint

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
    private var money:String? = null
    private val homeViewModel: PayingViewModel by viewModels()





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
        homeViewModel.money.observe(viewLifecycleOwner){
            money = it
           binding.balance.text = "$".plus(it)
        }
        homeViewModel.readMoney()
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
        setListeners()
    }
    private fun setListeners(){
        binding.continueBtn.setOnClickListener {
            val amount = binding.amountEt.text.toString()
            val description = binding.description.editText?.text.toString()
            if (!amount.isNullOrEmpty() && !description.isNullOrEmpty() && !secondValue.isNullOrEmpty() ){
                if(binding.spinnerCategoryExpense.isVisible){
                    val transaction = com.example.finaleproject.model.transaction.Transaction(amount.toDouble(),firstValue,description,thirdValue)
                    homeViewModel.saveTransaction(transaction)
                }else{
                    val transaction = com.example.finaleproject.model.transaction.Transaction(amount.toDouble(),firstValue,description,secondValue)
                    homeViewModel.saveTransaction(transaction)
                }
            }
        }
        homeViewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                Toast.makeText(context,"transaction saved",Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_payingFragment2_to_bottomFragment)
            }else{
                Toast.makeText(context,"transaction failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}