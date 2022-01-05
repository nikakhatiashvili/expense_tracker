package com.example.finaleproject.ui.auth.auth.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentResetBinding
import com.example.finaleproject.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment() {
    private var _binding: FragmentVerifyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentVerifyBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }

    private fun bind(){
        binding.backToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_verifyFragment_to_loginFragment)
        }
    }

}