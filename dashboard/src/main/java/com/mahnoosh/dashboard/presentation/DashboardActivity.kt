package com.mahnoosh.dashboard.presentation

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.mahnoosh.dashboard.R
import com.mahnoosh.dashboard.data.di.DataSourceModule
import com.mahnoosh.dashboard.data.di.PersistentModule
import com.mahnoosh.dashboard.data.di.RepositoryModule
import com.mahnoosh.dashboard.data.di.ViewModelModule
import com.mahnoosh.dashboard.databinding.ActivityDashboardBinding
import com.mahnoosh.utils.extensions.showAlertDialog
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class DashboardActivity : AppCompatActivity(), MenuProvider {

    private lateinit var binding: ActivityDashboardBinding
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var toolbar: Toolbar
    private lateinit var menu: Menu

    private val viewModel by viewModel<DashboardViewModel>()

    private lateinit var dialog: AlertDialog
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
                    R.id.SignOut -> {
                        dialog = binding.root.showAlertDialog(
                            getString(R.string.signOut),
                            getString(R.string.sure_signOut),
                            okClicked = { _, _ ->
                                lifecycleScope.launch {
                                    viewModel.dashboardIntent.send(DashboardIntent.SignOut)
                                }
                            },
                            NoClicked = { _, _ ->
                                dialog.dismiss()
                                drawerLayout.close()
                            }).show()
                    }
                }
                true
            }
        }
        this?.addMenuProvider(this, this)
        setupUi()
    }

    private fun setupUi() {
        viewModel.state.flowWithLifecycle(this.lifecycle).onEach { dashboardState ->
            when (dashboardState) {
                is DashboardState.Loading -> {}
                is DashboardState.Categories -> {}
                is DashboardState.Error -> {}
                is DashboardState.SignOut -> {
                    val intent = Intent()
                    intent.setClassName(
                        binding.root.context,
                        "com.mahnoosh.dashboard.presentation.AuthActivity"
                    )
                    startActivity(intent)
                }
                else -> {}
            }

        }.launchIn(this.lifecycleScope)
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