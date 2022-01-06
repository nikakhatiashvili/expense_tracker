package com.example.finaleproject.ui.dashboard

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentDashboardBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val dashboardViewModel: DashboardViewModel by viewModels()
    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:ExchangeAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }

    private fun bind(){
        adapter = ExchangeAdapter()
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        dashboardViewModel.load()

        dashboardViewModel.characters.observe(viewLifecycleOwner){
            adapter.data = it
            binding.spinKit.visibility = View.GONE
        }

        dashboardViewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                binding.spinKit.visibility = View.VISIBLE
            }
        }

        dashboardViewModel.money.observe(viewLifecycleOwner){
            d("blablabla", it.toString())
            if (binding.amountEt.text?.isNotEmpty()!!){
                binding.valueTxt.text = it.toString()
            }else{
                binding.valueTxt.text = ""
            }
        }
        setArrayAdapters()
    }

    private fun setArrayAdapters(){
        val currency = resources.getStringArray(R.array.currency)
        val currencys = ArrayAdapter(requireContext(),R.layout.textview,currency)
        var firstValue = binding.dropdownCurrency.text.toString()
        var secondValue = binding.dropdownCurrencyTo.text.toString()
        with(binding){
            dropdownCurrency.setAdapter(currencys)
            dropdownCurrencyTo.setAdapter(currencys)

            dropdownCurrency.onItemClickListener = AdapterView.OnItemClickListener{ adapterView, view, position, l ->
                val selectedCurrency = currencys.getItem(position)
                firstValue = selectedCurrency.toString()
                if (!amountEt.text.isNullOrEmpty()){
                    dashboardViewModel.convertValue(amountEt.text.toString().toDouble(),firstValue,secondValue)
                }
            }
            dropdownCurrencyTo.onItemClickListener = AdapterView.OnItemClickListener{adapterView, view, position, l ->
                val selectedCurrency = currencys.getItem(position)
                secondValue = selectedCurrency.toString()
                if (!amountEt.text.isNullOrEmpty()){
                    dashboardViewModel.convertValue(amountEt.text.toString().toDouble(),firstValue,secondValue)
                }
            }
            amountEt.doAfterTextChanged {
                if (amountEt.text.toString() != "0" && !binding.amountEt.text.isNullOrEmpty()){
                    val amount = amountEt.text.toString().toDouble()
                    dashboardViewModel.convertValue(amount,firstValue,secondValue)
                }
            }
        }


    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}