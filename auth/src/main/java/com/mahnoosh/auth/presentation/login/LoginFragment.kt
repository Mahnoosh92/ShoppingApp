package com.mahnoosh.auth.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahnoosh.auth.R
import com.mahnoosh.auth.databinding.FragmentLoginBinding
import com.mahnoosh.auth.databinding.FragmentSplashBinding
import com.mahnoosh.auth.presentation.splash.SplashIntent
import com.mahnoosh.auth.presentation.splash.SplashViewModel
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.utils.extensions.shortSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : BaseFragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun setupUi() {
        /*NO_OP*/
    }

    override fun setupCollectors() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.observe(viewLifecycleOwner){loginState->
                    when(loginState) {
                        is LoginState.NoAccount -> {
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                        }
                        is LoginState.LoginStatus -> {
                            if(loginState.status) {
                                val intent = Intent()
                                intent.setClassName(
                                    binding.root.context,
                                    "com.mahnoosh.dashboard.presentation.DashboardActivity"
                                )
                                startActivity(intent)
                            }
                        }
                        is LoginState.Error -> {
                            binding.root.shortSnackBar(loginState.error.toString())
                        }
                        is LoginState.Loading -> {}
                    }
                }
            }
        }
    }

    override fun setupListeners() {
        with(binding) {
            noAccount.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.loginIntent.send(LoginIntent.CreateAccount)
                }
            }
            login.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.loginIntent.send(LoginIntent.Login(emailLogin.text.toString(), passwordLogin.text.toString()))
                }
            }
        }
    }
}