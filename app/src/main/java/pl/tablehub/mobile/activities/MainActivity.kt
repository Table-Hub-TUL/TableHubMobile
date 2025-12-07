package pl.tablehub.mobile.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.repository.AuthRepository
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var authRepository: AuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            handleAuth()
        }
    }

    private fun handleAuth() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment
        val navController = navHostFragment?.navController ?: return

        lifecycleScope.launch {
            if (authRepository.hasValidToken()) {
                navigateToMain(navController)
            } else {
                if (authRepository.hasRefreshToken()) {
                    val refreshSuccess = authRepository.attemptRefreshToken()

                    if (refreshSuccess) {
                        navigateToMain(navController)
                    } else {
                        navigateToLogin(navController)
                    }
                } else {
                    authRepository.clearData()
                    navigateToLogin(navController)
                }
            }
        }
    }

    private suspend fun navigateToMain(navController: NavController) {
        val token = authRepository.getJWT().first()

        if (token != null) {
            navController.navigate(
                R.id.mainViewFragment,
                bundleOf(Pair(NavArgs.JWT, token)),
                navOptions {
                    popUpTo(R.id.logInFragment) { inclusive = true }
                    launchSingleTop = true
                }
            )
        } else {
            navigateToLogin(navController)
        }
    }

    private fun navigateToLogin(navController: NavController) {
        navController.navigate(
            R.id.logInFragment,
            null,
            navOptions {
                popUpTo(R.id.mainViewFragment) { inclusive = true }
                launchSingleTop = true
            }
        )
    }
}