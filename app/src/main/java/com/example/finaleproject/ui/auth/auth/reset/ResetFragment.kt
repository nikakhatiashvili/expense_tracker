package com.example.finaleproject.ui.auth.auth.reset

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentResetBinding
import com.example.finaleproject.databinding.LoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetFragment : Fragment() {

    private var _binding: FragmentResetBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResetBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }

    private fun bind(){

    }

}