package com.example.finaleproject.ui.auth.auth.register


import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentRegisterBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val viewModel: RegisterViewModel by activityViewModels()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }
    private fun bind(){
        val text = "<font color='black'>By signing up, you agree to the </font><font color='purple'>Terms of Service and Privacy Policy!</font>"
        binding.bySigning.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE)
        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        listeners()
    }

    private fun listeners(){
        binding.signUpBtn.setOnClickListener {
            val email = binding.emailEtSignUp.editText?.text.toString()
            val pass = binding.passwordEtSignUp.editText?.text.toString()
            val name = binding.nameEtSignUp.editText?.text.toString()
            if (checkBox()){
                if(checkStrings(name, pass, email)){
                    firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful){
                            val firebaseUser:FirebaseUser = task.result!!.user!!
                            firebaseUser.sendEmailVerification()
                            viewModel.addMoney()
                            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                        }else{
                            Toast.makeText(activity,task.exception?.message.toString(),Toast.LENGTH_SHORT).show()
                        }
                    })
                }
            }
        }
    }

    private fun checkBox()=binding.checkBox.isChecked

    private fun checkStrings(name:String,pass:String,email:String):Boolean{
       if(name.isNullOrEmpty() || pass.isNullOrEmpty() || email.isNullOrEmpty()){
           return false
       }
        return true
    }
}