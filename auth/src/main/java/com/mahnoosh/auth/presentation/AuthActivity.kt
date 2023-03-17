package com.mahnoosh.auth.presentation

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mahnoosh.auth.R
import com.mahnoosh.auth.data.di.dataSourceModule
import com.mahnoosh.auth.data.di.repositoryModule
import com.mahnoosh.auth.data.di.useCaseModule
import com.mahnoosh.auth.data.di.viewModelModule
import com.mahnoosh.auth.databinding.ActivityAuthBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(listOf(dataSourceModule, repositoryModule, viewModelModule, useCaseModule))
        //top toolbar
        setSupportActionBar(findViewById(R.id.my_toolbar))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.loginFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        setupNav()
    }

    private fun setupNav() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.splashFragment, R.id.loginFragment-> hideAppbar()
                else -> showAppbar()
            }
        }
    }
    private fun showAppbar() {
        binding.appbar.visibility = View.VISIBLE
    }

    private fun hideAppbar() {
        binding.appbar.visibility = View.GONE
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(listOf(dataSourceModule, repositoryModule, viewModelModule, useCaseModule))
    }
}