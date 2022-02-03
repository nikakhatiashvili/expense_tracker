package com.example.finaleproject.ui.expense.income

import android.annotation.SuppressLint
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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentPayingBinding
import com.example.finaleproject.model.pieChartExpense
import com.example.finaleproject.model.pieChartIncome
import com.example.finaleproject.util.UIHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

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
            binding.constraintLayout.setBackgroundColor(resources.getColor(R.color.light_green))
            binding.spinnerCategoryExpense.visibility = View.GONE
            binding.spinnerCategoryIncome.visibility = View.VISIBLE
        }else{
            binding.constraintLayout.setBackgroundColor(resources.getColor(R.color.light_red))
            binding.spinnerCategoryIncome.visibility = View.GONE
            binding.spinnerCategoryExpense.visibility = View.VISIBLE
        }
    }

    private fun bind(){
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.moneys.collect{
                money = it
                binding.balance.text = "$".plus(it)
            }
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
    @SuppressLint("SimpleDateFormat")
    private fun setListeners(){
        binding.continueBtn.setOnClickListener {
            val amount = binding.amountEt.text.toString()
            var description = binding.description.editText?.text.toString()
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())
            if (!amount.isNullOrEmpty() && !secondValue.isNullOrEmpty()){
                if(binding.spinnerCategoryExpense.isVisible){
                    if(money?.toInt()?.minus(amount.toInt())!! > 0 || money?.toInt()?.minus(amount.toInt()) == 0 ){
                        amount.toString().trim{ it <= ' '}
                        val transaction = com.example.finaleproject.model.transaction.Transaction(amount.toDouble(),firstValue,description,thirdValue,currentDate.toString())
                        val expense = pieChartExpense(amount.toInt(),thirdValue)
                        viewLifecycleOwner.lifecycleScope.launch {
                            homeViewModel.saveTransaction(transaction)
                            homeViewModel.saveExpense(expense)

                        }
                        homeViewModel.changeMoney(amount,money)
                    }
                }else{
                    amount.toString().trim{ it <= ' '}
                    val transaction = com.example.finaleproject.model.transaction.Transaction(amount.toDouble(),firstValue,description,secondValue,currentDate.toString())
                    viewLifecycleOwner.lifecycleScope.launch {
                        homeViewModel.saveTransaction(transaction)
                        val income = pieChartIncome(amount.toInt(),secondValue)
                        homeViewModel.saveIncome(income)
                    }
                    homeViewModel.increaseMoney(amount,money)
                }
            }else{
                Toast.makeText(requireContext(),getString(R.string.amountorsecvalueisnull),Toast.LENGTH_SHORT).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel._isLoading.collect{
                if (it){
                    Toast.makeText(context,"transaction saved",Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_payingFragment2_to_bottomFragment)
                }else{
                    Toast.makeText(context,"transaction failed",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}