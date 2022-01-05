package com.example.finaleproject.ui.auth.auth.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentPasswordBinding
import com.example.finaleproject.databinding.FragmentVerifyBinding
import com.example.finaleproject.extensions.checkEmail
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PasswordFragment : Fragment() {

    private var _binding: FragmentPasswordBinding? = null

    private val binding get() = _binding!!

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }
    private fun bind(){
        binding.continueReset.setOnClickListener {
            val email = binding.emailEtReset.editText?.text.toString()
            if (email.isNullOrEmpty()){
                Toast.makeText(activity,"email is either empty or badly formatted",Toast.LENGTH_SHORT).show()
            }else{
                firebaseAuth.sendPasswordResetEmail(email)
                findNavController().navigate(R.id.action_passwordFragment_to_verifyFragment)
            }

        }
    }
}