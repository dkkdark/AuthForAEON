package com.example.authforaeon.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.authforaeon.R
import com.example.authforaeon.databinding.ActivityMainBinding
import com.example.authforaeon.presentation.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = getNavController()
        setupStartDestination(navController)
        viewModel.processEvents(MainEvent.IsLogin)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                launch {
                    viewModel.states.collect {
                        when(it) {
                            is IsLoginStates.LoggedIn -> {
                                navController.popBackStack(R.id.loadingFragment, true)
                                navController.navigate(R.id.loginFragment)
                            }
                            is IsLoginStates.NotLoggedIn -> {
                                navController.popBackStack(R.id.loadingFragment, true)
                                navController.navigate(R.id.paymentListFragment)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getNavController(): NavController {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_content) as NavHostFragment
        return navHostFragment.navController
    }

    private fun setupStartDestination(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(
            R.id.loadingFragment
        )
        navController.graph = graph
    }
}