package com.example.finaleproject.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentDashboardBinding
import com.example.finaleproject.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch





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
    ): View {
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
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.exchangeResponse.collect {
                when (it) {
                    is Resource.Loading -> {
                        binding.spinKit.visibility = View.VISIBLE
                    }
                    is Resource.Success -> {
                        binding.spinKit.visibility = View.GONE
                        adapter.data = it.data!!
                        binding.recyclerview.startLayoutAnimation()
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            dashboardViewModel.moneys.collectLatest{
                if (binding.amountEt.text?.isNotEmpty()!!){
                    binding.valueTxt.text = it.toString()
                }else{
                    binding.valueTxt.text = ""
                }
            }
        }
        setArrayAdapters()
    }

    private fun setArrayAdapters(){
        val clear = ArrayAdapter(requireContext(),R.layout.textview,resources.getStringArray(R.array.clear))
        val currency = resources.getStringArray(R.array.currency)
        ArrayAdapter(requireContext(),R.layout.textview,currency)
        var firstValue = "USD"
        var secondValue = "GEL"
        with(binding){
            spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selected = p0?.getItemAtPosition(p2).toString()
                     firstValue = selected
                    if (!amountEt.text.isNullOrEmpty()){
                        if (checkZero(amountEt.text.toString())){
                            if (!dashboardViewModel.containsError(amountEt.text.toString()) ){
                                    dashboardViewModel.convertValue(
                                        amountEt.text.toString().toDouble(), firstValue, secondValue
                                    )
                                }
                        }
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            spinnerto.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selected = p0?.getItemAtPosition(p2).toString()
                    secondValue = selected
                    if (!amountEt.text.isNullOrEmpty()){
                        if (checkZero(amountEt.text.toString())){
                            if(!dashboardViewModel.containsError(amountEt.text.toString()) ){
                                dashboardViewModel.convertValue(amountEt.text.toString().toDouble(),firstValue,secondValue)
                            }
                        }
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            amountEt.doAfterTextChanged {
                if (amountEt.text.toString() != "0" && !binding.amountEt.text.isNullOrEmpty()){
                    if (checkZero(amountEt.text.toString())){
                        if(!dashboardViewModel.containsError(amountEt.text.toString())){
                            val amount = amountEt.text.toString().toDouble()
                            dashboardViewModel.convertValue(amount,firstValue,secondValue)
                        }
                    }
                }
            }
        }
    }
    fun checkZero(string:String)= string.first().toString() != "0"


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}