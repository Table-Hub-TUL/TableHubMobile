package pl.tablehub.mobile.fragments.login

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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.client.rest.interfaces.IAuthService
import pl.tablehub.mobile.client.rest.RetrofitClient
import pl.tablehub.mobile.client.model.LoginRequest
import pl.tablehub.mobile.client.model.LoginResponse
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.fragments.login.composables.MainLoginView
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@AndroidEntryPoint
class LogInFragment : Fragment() {

    @Inject
    lateinit var encryptedPreferences: EncryptedDataStore

    private val authService: IAuthService by lazy {
        RetrofitClient.client.create(IAuthService::class.java)
    }

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
        authService.loginUser(loginRequest)?.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        lifecycleScope.launch {
                            encryptedPreferences.storeJWT(loginResponse.token)
                            findNavController().navigate(
                                R.id.mainViewFragment,
                                bundleOf(Pair(NavArgs.JWT, encryptedPreferences.getJWT().first()!!)),
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
                    Toast.makeText(context, "${requireContext().getString(R.string.login_failed)}: " +
                            "${R.string.invalid_cred}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(context, requireContext().getString(R.string.login_failed), Toast.LENGTH_LONG).show()
            }
        })
    }
}