package com.example.finaleproject.ui.auth.auth.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.LoginBinding
import com.example.finaleproject.util.Resource
import com.example.finaleproject.util.UIHelper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private  val viewModel: LoginViewModel by viewModels()
    private var _binding: LoginBinding? = null
    var checked:Boolean? = null
    var user:FirebaseUser? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
        private val binding get() = _binding!!
    @Inject
    lateinit var FirebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = FirebaseAuth.currentUser
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }

    private fun bind(){
        checked = user != null
        if (checked!!){
            findNavController().navigate(R.id.action_loginFragment_to_bottomFragment)
        }

        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_passwordFragment)
        }
        login()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loginFlow.collect{
                when(it){
                    is Resource.Success -> {
                        findNavController().navigate(R.id.action_loginFragment_to_bottomFragment)
                    }
                    is Resource.Error ->{
                        Toast.makeText(requireContext(),it.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun login(){
        binding.materialButton.setOnClickListener {
            if (UIHelper.checkLogin(binding.emailEtLogin.editText?.text.toString(),binding.passwordEtLogin.editText?.text.toString())){
                val email = binding.emailEtLogin.editText?.text.toString().trim{ it <= ' '}
                val password:String = binding.passwordEtLogin.editText?.text.toString().trim{ it <= ' '}
                viewModel.login(email,password)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}