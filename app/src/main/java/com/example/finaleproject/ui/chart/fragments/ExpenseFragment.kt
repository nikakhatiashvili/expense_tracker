package com.example.finaleproject.ui.chart.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Half.toFloat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getColor

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.finaleproject.R
import com.example.finaleproject.databinding.ExpenseFragmentBinding
import com.example.finaleproject.model.pieChartExpense
import com.example.finaleproject.model.transaction.Transaction
import com.example.finaleproject.ui.home.HomeViewModel
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.coroutines.launch
import java.util.concurrent.Flow
import android.R.attr.data
import com.github.mikephil.charting.components.Legend


class ExpenseFragment : Fragment() {

    private var _binding: ExpenseFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by activityViewModels()
    val foodStr = "Food"
    val ShoppingStr = "Shopping"
    val TransportationStr = "Transportation"
    val SubscriptionStr = "Subscription"
    val RentStr = "Rent"
    val HealthStr = "Health care"
    val EducationStr = "Education"


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ExpenseFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }

    private fun bind() {
        var expense: List<pieChartExpense> = emptyList()
        viewModel.getExpense()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.expenseResponse.collect {
                expense = it
                setPie(expense)
            }
        }
    }
    private fun setPie(expense: List<pieChartExpense>){
        var Food: Int = 0
        var Shopping: Int = 0
        var Transportation: Int = 0
        var Subscription: Int = 0
        var Rent: Int = 0
        var Health: Int = 0
        var Education: Int = 0
        for (i in expense) {
            if (i.category == foodStr) {
                Food += i.money!!
            }else if(i.category == ShoppingStr){
                Shopping += i.money!!
            }else if(i.category == TransportationStr){
                Transportation += i.money!!
            }else if(i.category == SubscriptionStr){
                Subscription += i.money!!
            }else if(i.category == RentStr){
                Rent += i.money!!
            }else if(i.category == HealthStr){
                Health += i.money!!
            }else if(i.category == EducationStr){
                Education += i.money!!
            }
        }
        setChart(Food.toFloat(),Shopping.toFloat(),Transportation.toFloat(),Subscription.toFloat(),Rent.toFloat(),Health.toFloat(),Education.toFloat())
    }
    private fun setChart(food:Float,shop:Float,transport:Float,sub:Float,rent:Float,health:Float,education:Float){
        val expenses = listOf<String>("Food","Shopping","Transportation","Subscription","Rent","Health","Education")
        val yValues = listOf<Float>(food,shop,transport,sub,rent,health,education)
        val piechartentry = mutableListOf<PieEntry>()
        for ((i,item) in yValues.withIndex())
        {
            piechartentry.add(PieEntry(item, i.toFloat()))
        }
        val entries: MutableList<PieEntry> = ArrayList()
        val pieChart = binding.piechart
        if(food.toInt() == 0)
        entries.add(PieEntry(food, "food"))
        entries.add(PieEntry(shop, "shop"))
        entries.add(PieEntry(transport, "transport"))
        entries.add(PieEntry(sub, "Subscription"))
        entries.add(PieEntry(rent, "Rent"))
        entries.add(PieEntry(health, "Health"))
        entries.add(PieEntry(education, "Education"))

        val colors= listOf<Int>(resources.getColor(R.color.light_red),
                resources.getColor(R.color.transaction_yellow),
                resources.getColor(R.color.transaction_blue),
                resources.getColor(R.color.transaction_pink) ,
                resources.getColor(R.color.green),
                resources.getColor(R.color.transaction_rent),
                resources.getColor(R.color.black))
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