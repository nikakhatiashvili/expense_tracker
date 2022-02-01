package com.example.finaleproject.ui.transactions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.finaleproject.R
import com.example.finaleproject.databinding.ExpenseFragmentBinding
import com.example.finaleproject.databinding.FragmentNotificationsBinding
import com.example.finaleproject.databinding.TransactionItemBinding
import com.example.finaleproject.databinding.TransactionsBinding
import com.example.finaleproject.ui.chart.fragments.ExpenseFragment
import com.example.finaleproject.ui.chart.fragments.IncomeFragment
import com.example.finaleproject.ui.home.HomeViewModel
import com.example.finaleproject.ui.home.TransactionAdapter
import com.example.finaleproject.ui.notifications.CryptoAdapter
import com.example.finaleproject.ui.notifications.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TransactionsFragment: Fragment() {

    private var _binding: TransactionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: TransactionAdapter
    private lateinit var viewPager: ViewPagerAdapter
    private val homeViewModel: HomeViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TransactionsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }
    private fun bind(){
        homeViewModel.getTransactions()
        adapter = TransactionAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerview.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            homeViewModel.exchangeResponse.collect{
                adapter.data = it.asReversed()
                if(adapter.data.isEmpty()){
                    binding.textView18.visibility = View.VISIBLE
                }else{
                    binding.textView18.visibility = View.GONE
                }
            }
        }
        binding.gobackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_transactionsFragment_to_profileFragment)
        }
        val fragments = listOf<Fragment>(ExpenseFragment(),IncomeFragment())

        viewPager = ViewPagerAdapter(fragmentManager!!,lifecycle)
        binding.viewPager.adapter = viewPager
        viewPager.data = fragments
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}