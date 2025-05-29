package pl.tablehub.mobile.activities

import android.os.Bundle
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

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val encryptedPreferences = EncryptedDataStore(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if(savedInstanceState == null) {
            handleAuth()
        }
    }

    private fun handleAuth() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.logInFragment) as? NavHostFragment
        lifecycleScope.launch {
            if (encryptedPreferences.hasValidToken()) {
                navHostFragment?.navController?.navigate(R.id.mainViewFragment, bundleOf(
                    Pair(NavArgs.JWT, encryptedPreferences.getJWT().first()!!)
                ))
            } else {
                navHostFragment?.navController?.navigate(R.id.logInFragment)
            }
        }
    }
}