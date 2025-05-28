package pl.tablehub.mobile.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.signup.composables.MainSignUpView
import pl.tablehub.mobile.ui.theme.TableHubTheme

@AndroidEntryPoint
class SignUpFragment : Fragment() {

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
                            handleSignUp(username, email, password)
                        },
                        onBackToLogin = {
                            navigateToLogin()
                        }
                    )
                }
            }
        }
    }

    private fun handleSignUp(username: String, email: String, password: String) {
        // TODO: Implement actual sign-up logic here
        // This could involve calling a ViewModel, Repository, or API service

        // Navigate to login after successful signup
        navigateToLogin()
    }

    private fun navigateToLogin() {
        try {
            findNavController().navigate(R.id.action_signUpFragment_to_logInFragment)
        } catch (e: Exception) {
            findNavController().popBackStack()
        }
    }
}