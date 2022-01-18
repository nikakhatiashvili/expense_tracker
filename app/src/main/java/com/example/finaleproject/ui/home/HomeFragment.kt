package com.example.finaleproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaleproject.databinding.FragmentHomeBinding
import com.example.finaleproject.model.transaction.Transaction
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:TransactionAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }


    private fun bind(){
        homeViewModel.getTransactions()
        adapter = TransactionAdapter()
        var money : List<Transaction> = emptyList()
        var income:Int = 0
        var expense:Int = 0
        var counter = 0
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.transactionRecyclerview.layoutManager = linearLayoutManager
        binding.transactionRecyclerview.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.exchangeResponse.collect{
               money = it
                if (it.size > 10){
                    binding.seemore.visibility = View.VISIBLE
                }
                for(i in money){
                    if (i.category == "Income"){
                        income += i.amount?.toInt()!!

                    }else{
                        expense += i.amount?.toInt()!!
                    }
                    counter += 1
                }
                binding.money.text = "$".plus(income.toString())
                binding.expenseMoney.text = "$".plus(expense.toString())
                income = 0
                expense = 0
                adapter.data = it.asReversed()
                if(adapter.data.isEmpty()){
                    binding.textView18.visibility = View.VISIBLE
                }else{
                    binding.textView18.visibility = View.GONE
                }
            }
        }
        binding.seemore.visibility = View.GONE
        if(adapter.data.isEmpty()){
            binding.textView18.visibility = View.VISIBLE
        }else{
            binding.textView18.visibility = View.GONE
        }

        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.moneys.collect{
                binding.coinItemPriceTextView.text = "$".plus(it)
            }
        }
        homeViewModel.readMoney()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}