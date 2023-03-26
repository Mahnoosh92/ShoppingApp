package com.mahnoosh.auth.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.mahnoosh.auth.R
import com.mahnoosh.auth.databinding.FragmentSplashBinding
import com.mahnoosh.auth.presentation.AuthActivity
import com.mahnoosh.core.base.BaseFragment
import com.mahnoosh.utils.extensions.shortSnackBar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashFragment : BaseFragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUi() {
        lifecycleScope.launch {
            viewModel.splashIntent.send(SplashIntent.GetUser)
        }
    }

    override fun setupCollectors() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { splashState ->
                    when (splashState) {
                        is SplashState.Loading -> {
                            binding.splashLottie.isVisible = true
                        }
                        is SplashState.User -> {
                            if (splashState.user == null) {
                                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
                            } else {
                                val intent = Intent()
                                intent.setClassName(
                                    binding.root.context,
                                    "com.mahnoosh.dashboard.presentation.DashboardActivity"
                                )
                                startActivity(intent)
                                (requireActivity() as AuthActivity).finish()
                            }
                        }
                        is SplashState.Error -> {
                            binding.root.shortSnackBar(getString(R.string.general_error))
                        }
                    }
                }
            }
        }
    }

    override fun setupListeners() {
        /*NO_OP*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}