package com.example.finaleproject.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentNotificationsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: CryptoAdapter
    private val viewModel:NotificationsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }



    private fun bind(){
        adapter = CryptoAdapter {
            val Bundle = bundleOf("ability" to it)
            findNavController().navigate(R.id.action_notificationsFragment_to_cryptoFragment,Bundle)
        }
        binding.apply {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = LinearLayoutManager(requireContext())
        }

        viewModel.getCrypto()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cryptoFlow.collect{
                when(it){
                    is ApiState.Success ->{
                        adapter.data = it.data
                        binding.recyclerview.startLayoutAnimation()
                        binding.spinKit.visibility = View.GONE
                    }
                    is ApiState.Loading ->{
                        binding.spinKit.visibility = View.VISIBLE
                    }
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
