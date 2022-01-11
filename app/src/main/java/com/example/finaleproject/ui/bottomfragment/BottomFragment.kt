package com.example.finaleproject.ui.bottomfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.finaleproject.R
import com.example.finaleproject.databinding.FragmentBottomBinding
import com.example.finaleproject.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class BottomFragment : Fragment() {
    private val viewModel:HomeViewModel by activityViewModels()
    private var _binding: FragmentBottomBinding? = null
    private var money:String? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomBinding.inflate(inflater, container, false)
        val root: View = binding.root
        bind()
        return root
    }


    private fun bind(){

        val navController = childFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        binding.navView.setupWithNavController(navController.navController)
        binding.navView.menu.getItem(2).isEnabled = false
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_bottomFragment_to_payingFragment2)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel._loggedIn.collect{
                if (it){
                    findNavController().navigate(R.id.action_bottomFragment_to_loginFragment)
                    viewModel.changeLogged()
                }
            }
        }
    }
}

