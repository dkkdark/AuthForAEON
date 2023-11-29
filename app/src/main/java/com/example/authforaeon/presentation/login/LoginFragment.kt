package com.example.authforaeon.presentation.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.authforaeon.R
import com.example.authforaeon.databinding.FragmentLoginBinding
import com.example.authforaeon.utils.showSnackbar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginButton.setOnClickListener {
                viewModel.processEvents(LoginEvent.Login(loginText.text.toString(), passwordText.text.toString()))
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.states.collect {
                        when(it) {
                            is LoginStates.Loading -> { /* TODO: progress bar */ }
                            is LoginStates.Success -> {
                                view.showSnackbar(it.success)
                                navigateToPaymentList()
                            }
                            is LoginStates.Error -> {
                                view.showSnackbar(it.error)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun navigateToPaymentList() {
        findNavController().popBackStack(R.id.loginFragment, true)
        findNavController().navigate(R.id.paymentListFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}