package com.example.finaleproject.ui.chart.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.finaleproject.R
import com.example.finaleproject.databinding.IncomeFragmentBinding
import com.example.finaleproject.databinding.TransactionsBinding
import com.example.finaleproject.model.pieChartExpense
import com.example.finaleproject.model.pieChartIncome
import com.example.finaleproject.ui.home.HomeViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch
import java.util.concurrent.Flow

class IncomeFragment:Fragment() {

    private var _binding: IncomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    val salaryString = "Salary"
    val passiveString = "Passive income"
    val Gift = "Gift"
    var salary: Int = 0
    var passive: Int = 0
    var gift: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = IncomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }
    private fun bind(){
        var expense: List<pieChartIncome> = emptyList()
        viewModel.getIncome()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.incomeResponse.collect {
                expense = it
                setPie(expense)
            }
        }
    }
    private fun setPie(income:List<pieChartIncome>){
        for (i in income) {
            if (i.category == salaryString) {
                salary += i.money!!
            }else if(i.category == passiveString){
                passive += i.money!!
            }else if(i.category == Gift){
                gift += i.money!!
            }
        }
        val colors : MutableList<Int> = ArrayList()
        val entries: MutableList<PieEntry> = ArrayList()
        val pieChart = binding.piechart
        if (salary.toInt() > 0){
            entries.add(PieEntry(salary.toFloat(), "Salary"))
            colors.add(resources.getColor(R.color.transaction_green))
        }
        if (gift.toInt() > 0){
            entries.add(PieEntry(gift.toFloat(), "gift"))
            colors.add(resources.getColor(R.color.transaction_pink))
        }
        if (passive.toInt() > 0){
            entries.add(PieEntry(passive.toFloat(), "passive income"))
            colors.add(resources.getColor(R.color.transaction_blue))
        }
        val piedataset = PieDataSet(entries,"transaction chart")
        val data = PieData(piedataset)
        piedataset.colors = colors
        piedataset.sliceSpace=3f
        pieChart.data = data
        pieChart.holeRadius = 5f
        pieChart.setBackgroundColor(resources.getColor(R.color.background))
        pieChart.animateY(1000)
    }
}