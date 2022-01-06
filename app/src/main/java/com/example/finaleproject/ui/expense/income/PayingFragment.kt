package com.example.finaleproject.ui.expense.income

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentDashboardBinding
import com.example.finaleproject.databinding.FragmentPayingBinding


class PayingFragment : Fragment() {


    private var _binding: FragmentPayingBinding? = null
    private val binding get() = _binding!!
    val categoryes:String = "Income"
    var  selectedIncome:String?  = null
    var  selectedExpense:String?  = null

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
            var firstValue = binding.dropdownCategory.text.toString()
            var secondValue = binding.dropdownExpense.text.toString()
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
    }

}