package com.example.authforaeon.presentation.payment

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.authforaeon.R
import com.example.authforaeon.databinding.FragmentPaymentListBinding
import com.example.authforaeon.presentation.payment.recyclerview.PaymentListAdapter
import com.example.authforaeon.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentListFragment : Fragment() {

    private var _binding: FragmentPaymentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PaymentListAdapter()

        binding.apply {
            paymentRecyclerView.layoutManager = LinearLayoutManager(context)
            paymentRecyclerView.setHasFixedSize(true)
            paymentRecyclerView.setItemViewCacheSize(20)

            paymentRecyclerView.adapter = adapter
            adapter.submitList(listOf())

            logoutButton.setOnClickListener {
                viewModel.processEvents(PaymentEvent.Logout)
            }
        }

        viewModel.processEvents(PaymentEvent.GetPayment)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.states.collect {
                        when(it) {
                            is PaymentStates.Loading -> {
                            }
                            is PaymentStates.Error -> {
                                view.showSnackbar(it.error)
                            }
                            is PaymentStates.NavigateToLogin -> {
                                navigateToLoginFragment()
                            }
                        }
                    }
                }
                launch {
                    viewModel.payments.collect {
                        adapter.submitList(it)
                    }
                }
            }
        }
    }

    private fun navigateToLoginFragment() {
        findNavController().popBackStack(R.id.paymentListFragment, true)
        findNavController().navigate(R.id.loginFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}