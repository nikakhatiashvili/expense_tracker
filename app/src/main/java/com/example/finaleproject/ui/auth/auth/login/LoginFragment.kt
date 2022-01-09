package com.example.finaleproject.ui.auth.auth.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.LoginBinding
import com.example.finaleproject.ui.home.HomeViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: LoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
        private val binding get() = _binding!!

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

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
        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.forgotPass.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_passwordFragment)
        }
        findNavController().navigate(R.id.action_loginFragment_to_bottomFragment)
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

                    firebaseAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                Toast.makeText(activity,"you are logged in successfully.", Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_loginFragment_to_bottomFragment)
                            }else{
                                Toast.makeText(activity,task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                            }
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