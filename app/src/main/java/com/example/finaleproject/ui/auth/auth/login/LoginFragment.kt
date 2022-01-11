package com.example.finaleproject.ui.auth.auth.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.LoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
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
        viewModel.loggedIn.observe(viewLifecycleOwner){
            if (it){
                if(checked!!){
//                    viewModel.remember()
                }
                findNavController().navigate(R.id.action_loginFragment_to_bottomFragment)

            }
        }
        login()
    }

    private fun login(){
        binding.materialButton.setOnClickListener {
            when{
                TextUtils.isEmpty(binding.emailEtLogin.editText?.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(activity,"please enter email.", Toast.LENGTH_SHORT).show()
                }
                TextUtils.isEmpty(binding.passwordEtLogin.editText?.text.toString().trim{ it <= ' '}) -> {
                    Toast.makeText(activity,"please enter password.", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email = binding.emailEtLogin.editText?.text.toString().trim{ it <= ' '}
                    val password:String = binding.passwordEtLogin.editText?.text.toString().trim{ it <= ' '}
                    viewModel.login(email,password)

                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}