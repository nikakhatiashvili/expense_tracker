package com.example.finaleproject.ui.auth.auth.code

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finaleproject.databinding.FragmentCodeBinding
import com.example.finaleproject.ui.auth.auth.register.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SetCodeFragment:Fragment() {

    private var _binding: FragmentCodeBinding? = null
    private val binding get() = _binding!!
    private val viewModel:RegisterViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCodeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
}