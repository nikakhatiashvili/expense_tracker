package com.example.finaleproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentHomeBinding
import com.example.finaleproject.model.transaction.Transaction

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

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
        val data = listOf<Transaction>(Transaction(100.0,"Income","Salary","this months salary"),
                    Transaction(10.0,"Expense","Food","went out to eat"),
                    Transaction(25.2,"Expense","Shopping","went out for Shopping"),
                    Transaction(76.5,"Expense","Education","buy course on udemy"))

        binding.coinsItemImageView.setBackgroundColor(resources.getColor(R.color.transaction_green))
        adapter = TransactionAdapter()
        binding.transactionRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.transactionRecyclerview.adapter = adapter
        adapter.data = data
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}