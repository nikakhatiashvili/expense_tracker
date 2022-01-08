package com.example.finaleproject.ui.detailedcrypto

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.finaleproject.databinding.FragmentCryptoBinding
import com.example.finaleproject.model.CryptoItem
import com.example.finaleproject.util.ChartHelper
import com.example.finaleproject.util.UIHelper
import com.example.finaleproject.util.dollarString
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CryptoFragment : Fragment() {

    private var _binding: FragmentCryptoBinding? = null
    private val binding get() = _binding!!
    private  val viewModel:CryptoFragmentViewModel by viewModels()
    private lateinit var data:CryptoItem
//    private var symbolId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCryptoBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }
    private fun bind(){
        val data = arguments?.getParcelable<CryptoItem>("ability")
        d("crypto",data?.id.toString())
        viewModel.historicalData(data?.id)
        Glide.with(context!!).load(data?.image).into(binding.imageView)
//        binding.textView7.text = data?.name
        binding.coinItemNameTextView.text = data?.name
        binding.coinItemSymbolTextView.text = data?.symbol
        binding.coinItemPriceTextView.text = data?.current_price.dollarString()
        UIHelper.showChangePercent(binding.coinItemChangeTextView, data?.price_change_percentage_24h)
        viewModel.historicalData.observe(viewLifecycleOwner){
            ChartHelper.displayHistoricalLineChart(binding.lineChart.lineChart, data?.symbol.toString(), it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner){
            if (it){
                binding.coinListLoading.visibility = View.VISIBLE
            }else{
                binding.coinListLoading.visibility = View.GONE
            }
        }
    }
}