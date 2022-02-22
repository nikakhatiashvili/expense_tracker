package com.example.finaleproject.ui.auth.auth.password

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentPasswordBinding
import com.example.finaleproject.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PasswordFragment : Fragment() {

    private val viewModel:HomeViewModel by activityViewModels()
    private var _binding: FragmentPasswordBinding? = null
    private val binding get() = _binding!!

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
                Toast.makeText(activity,getString(R.string.emailBadlyFormatted),Toast.LENGTH_SHORT).show()
            }else{
                if (isValidEmail(email)){
                    viewModel.resetPass(email)
                    Toast.makeText(activity,getString(R.string.resetemail),Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(activity,getString(R.string.emailisnotvalid),Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    fun isValidEmail(target: CharSequence?): Boolean {
        return if (TextUtils.isEmpty(target)) {
            false
        } else {
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }
}