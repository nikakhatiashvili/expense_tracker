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
        val clear = ArrayAdapter(requireContext(),R.layout.textview,resources.getStringArray(R.array.clear))

        val currency = resources.getStringArray(R.array.currency)
        val currencys = ArrayAdapter(requireContext(),R.layout.textview,currency)
        var firstValue = "USD"
        var secondValue = "GEL"
        with(binding){
            spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val selected = p0?.getItemAtPosition(p2).toString()
                     firstValue = selected
                    if (!amountEt.text.isNullOrEmpty()){
                        dashboardViewModel.convertValue(amountEt.text.toString().toDouble(),firstValue,secondValue)
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
                        dashboardViewModel.convertValue(amountEt.text.toString().toDouble(),firstValue,secondValue)
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }
            }
            amountEt.doAfterTextChanged {
                if (amountEt.text.toString() != "0" && !binding.amountEt.text.isNullOrEmpty()){
                    val amount = amountEt.text.toString().toDouble()
                    dashboardViewModel.convertValue(amount,firstValue,secondValue)
                }
            }
            appCompatButton.setOnClickListener {
                val spinner1Index = binding.spinner.selectedItemPosition
                spinner.setSelection(binding.spinnerto.selectedItemPosition);
                binding.spinnerto.setSelection(spinner1Index);

            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}