package com.mahnoosh.product.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mahnoosh.product.data.di.ProductDataSourceModule
import com.mahnoosh.product.data.di.ProductRepositoryModule
import com.mahnoosh.product.data.di.ViewModelModule
import com.mahnoosh.product.databinding.ActivityProductBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadKoinModules(
            listOf(
                ProductDataSourceModule,
                ProductRepositoryModule,
                ViewModelModule
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(
            listOf(
                ProductDataSourceModule,
                ProductRepositoryModule,
                ViewModelModule
            )
        )
    }
}