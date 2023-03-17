package com.mahnoosh.auth.presentation.register

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.mahnoosh.auth.R
import com.mahnoosh.auth.databinding.FragmentLoginBinding
import com.mahnoosh.auth.databinding.FragmentRegisterBinding
import com.mahnoosh.auth.presentation.login.LoginViewModel
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.utils.extensions.shortSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment : BaseFragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {
    }

    override fun setupCollectors() {
        lifecycleScope.launch() {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.observe(viewLifecycleOwner) { registerState ->
                    when (registerState) {
                        is RegisterState.RegisterStatus -> {
                            if (registerState.status) {
                                val intent = Intent()
                                intent.setClassName(
                                    binding.root.context,
                                    "com.mahnoosh.dashboard.presentation.DashboardActivity"
                                )
                                startActivity(intent)
                            }
                        }
                        is RegisterState.Error -> {
                            binding.root.shortSnackBar(getString(R.string.general_error))
                        }
                        is RegisterState.Loading -> {}
                    }
                }
            }
        }
    }

    override fun setupListeners() {
        with(binding) {
            register.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.registerIntent.send(
                        RegisterIntent.RegisterUser(
                            emailRegister.text.toString(),
                            passwordRegister.text.toString()
                        )
                    )
                }
            }

        }
    }

}