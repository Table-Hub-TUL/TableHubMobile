package pl.tablehub.mobile.fragments.account.general.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rollbar.android.Rollbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.client.model.auth.SignUpRequest
import pl.tablehub.mobile.client.rest.interfaces.IAuthService
import pl.tablehub.mobile.fragments.account.general.signup.composables.MainSignUpView
import pl.tablehub.mobile.ui.theme.TableHubTheme
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    @Inject
    internal lateinit var authService: IAuthService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                TableHubTheme {
                    MainSignUpView(
                        onCreate = { username, email, password ->
                            handleSignUp(username, email, password, username)
                        },
                        onBackToLogin = {
                            navigateToLogin()
                        }
                    )
                }
            }
        }
    }

    private fun handleSignUp(username: String, email: String, password: String, nickname: String) {
        val signUpRequest = SignUpRequest(username = username, email = email, password = password, nickname = nickname)
        lifecycleScope.launch {
            try {
                val response = authService.signupUser(signUpRequest)
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), requireContext().getString(R.string.signup_ok), Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                } else {
                    Toast.makeText(requireContext(), requireContext().getString(R.string.signup_fail), Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                if (isAdded) {
                    Rollbar.instance().log(e)
                    Toast.makeText(requireContext(), requireContext().getString(R.string.signup_fail), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun navigateToLogin() {
        try {
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        } catch (e: Exception) {
            findNavController().popBackStack()
        }
    }
}