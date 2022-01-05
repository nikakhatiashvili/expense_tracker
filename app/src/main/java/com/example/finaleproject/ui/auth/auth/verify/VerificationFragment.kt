package com.example.finaleproject.ui.auth.auth.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentVerificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationFragment : Fragment() {
    private var _binding: FragmentVerificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }

    private fun bind(){
    binding.verifyBtn.setOnClickListener {
        findNavController().navigate(R.id.action_verificationFragment_to_loginFragment)
    }

    }

}