package com.example.finaleproject.ui.home

import android.os.Bundle
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter:TransactionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.getTransactions()
    }

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

    override fun onResume() {
        super.onResume()
    }

    private fun bind(){
        binding.coinsItemImageView.setBackgroundColor(resources.getColor(R.color.transaction_green))
        adapter = TransactionAdapter()

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.reverseLayout = true
//        linearLayoutManager.stackFromEnd = true
        binding.transactionRecyclerview.layoutManager = linearLayoutManager
        binding.transactionRecyclerview.adapter = adapter
        homeViewModel.crypto.observe(viewLifecycleOwner){
            d("list",it.toString())
            adapter.data = it
        }
        val time = "04:11"
        d("rest",time.substring(0,2))
        homeViewModel.money.observe(viewLifecycleOwner){
            binding.coinItemPriceTextView.text = "$".plus(it)
        }
        homeViewModel.readMoney()
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}