package com.mahnoosh.cart.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.mahnoosh.cart.data.di.CartDataSourceModule
import com.mahnoosh.cart.data.di.CartRepositoryModule
import com.mahnoosh.cart.data.di.CartViewModelModule
import com.mahnoosh.cart.databinding.ActivityCartBinding
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding

    private val viewModel by viewModel<CartViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(
            listOf(
                CartDataSourceModule, CartRepositoryModule, CartViewModelModule
            )
        )
        handleUi()
    }

    private fun handleUi() {
        lifecycleScope.launch {
            flowOf(
                flowOf(listOf(1, 2, 3))
                    .flatMapMerge {
                        it.asFlow()
                    }
                    .flatMapConcat {
                        Log.i("reza", "handleUi: $it")
                        flowOf(it)
                    }.toList()
            ).collect()
        }
//        lifecycleScope.launch {
//            viewModel.cartIntent.send(CartIntent.GetCarts)
//        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(
            listOf(
                CartDataSourceModule, CartRepositoryModule, CartViewModelModule
            )
        )
    }
}