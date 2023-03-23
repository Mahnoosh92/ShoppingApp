package com.mahnoosh.dashboard.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import com.mahnoosh.dashboard.R
import com.mahnoosh.dashboard.data.di.DataSourceModule
import com.mahnoosh.dashboard.data.di.PersistentModule
import com.mahnoosh.dashboard.data.di.RepositoryModule
import com.mahnoosh.dashboard.data.di.ViewModelModule
import com.mahnoosh.dashboard.databinding.ActivityDashboardBinding
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class DashboardActivity : AppCompatActivity(), MenuProvider {

    private lateinit var binding: ActivityDashboardBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar
    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadKoinModules(
            listOf(
                PersistentModule,
                DataSourceModule,
                RepositoryModule,
                ViewModelModule
            )
        )
        binding.apply {
            toggle = ActionBarDrawerToggle(
                this@DashboardActivity,
                drawerLayout,
                R.string.open,
                R.string.close
            )
            this@DashboardActivity.toolbar = toolbar
            setSupportActionBar(this@DashboardActivity.toolbar);
            drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

            supportActionBar?.setDisplayHomeAsUpEnabled(true)

            navView.setNavigationItemSelectedListener {
                when (it.itemId) {
                    com.mahnoosh.dashboard.R.id.Products -> {
                        Toast.makeText(
                            this@DashboardActivity,
                            "First Item Clicked",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    com.mahnoosh.dashboard.R.id.Profile -> {
                        Toast.makeText(
                            this@DashboardActivity,
                            "Second Item Clicked",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                    com.mahnoosh.dashboard.R.id.Categories -> {
                        Toast.makeText(
                            this@DashboardActivity,
                            "third Item Clicked",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
                true
            }
        }
        this?.addMenuProvider(this, this)
        setupUi()
    }

    private fun setupUi() {
        /*NO_OP*/
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(com.mahnoosh.dashboard.R.menu.top_manu, menu)
        this.menu = menu
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }


    override fun onDestroy() {
        super.onDestroy()
        unloadKoinModules(
            listOf(
                PersistentModule,
                DataSourceModule,
                RepositoryModule,
                ViewModelModule
            )
        )
    }
}