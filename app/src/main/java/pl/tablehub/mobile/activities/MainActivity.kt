package pl.tablehub.mobile.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import androidx.navigation.fragment.NavHostFragment
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var encryptedPreferences: EncryptedDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if(savedInstanceState == null) {
            handleAuth()
        }
    }

    private fun handleAuth() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main) as? NavHostFragment
        lifecycleScope.launch {
            if (encryptedPreferences.hasValidToken()) {
                navHostFragment?.navController?.navigate(R.id.action_logInFragment_to_mainViewFragment, bundleOf(
                    Pair(NavArgs.JWT, encryptedPreferences.getJWT().first()!!)
                ))
            } else {
                navHostFragment?.navController?.navigate(R.id.logInFragment)
            }
        }
    }
}