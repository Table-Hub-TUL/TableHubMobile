package pl.tablehub.mobile.fragments.account.general.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.rollbar.android.Rollbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.client.rest.interfaces.IAuthService
import pl.tablehub.mobile.client.model.auth.LoginRequest
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.fragments.account.general.login.composables.MainLoginView
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import javax.inject.Inject


@AndroidEntryPoint
class LogInFragment : Fragment() {

    @Inject
    lateinit var encryptedPreferences: EncryptedDataStore

    @Inject
    lateinit var authService: IAuthService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainLoginView(
                    onRegister = {
                        findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
                    },
                    onLogin = { username, password ->
                        handleLogin(username, password)
                    }
                )
            }
        }
    }

    private fun handleLogin(username: String, password: String) {
        val loginRequest = LoginRequest(username = username, password = password)

        lifecycleScope.launch {
            try {
                val response = authService.loginUser(loginRequest)

                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        encryptedPreferences.storeJWT(loginResponse.token)
                        val storedToken = encryptedPreferences.getJWT().first()

                        if (storedToken != null) {
                            findNavController().navigate(
                                R.id.mainViewFragment,
                                bundleOf(Pair(NavArgs.JWT, storedToken)),
                                navOptions {
                                    popUpTo(R.id.logInFragment) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            )
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "${requireContext().getString(R.string.login_failed)}: ${requireContext().getString(R.string.invalid_cred)}",
                        Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                if (isAdded) {
                    Rollbar.instance().log(e)
                    Toast.makeText(requireContext(), requireContext().getString(R.string.login_failed), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}