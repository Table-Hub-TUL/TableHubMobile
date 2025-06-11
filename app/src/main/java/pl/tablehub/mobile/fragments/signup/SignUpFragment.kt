package pl.tablehub.mobile.fragments.signup

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.client.rest.interfaces.IAuthService
import pl.tablehub.mobile.client.rest.RetrofitClient
import pl.tablehub.mobile.client.model.SignUpRequest
import pl.tablehub.mobile.client.model.SignUpResponse
import pl.tablehub.mobile.fragments.signup.composables.MainSignUpView
import pl.tablehub.mobile.ui.theme.TableHubTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@AndroidEntryPoint
class SignUpFragment : Fragment() {

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
            authService.signupUser(signUpRequest).enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, requireContext().getString(R.string.signup_ok), Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    } else {
                        Toast.makeText(context, requireContext().getString(R.string.signup_fail), Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.e("ERROR", t.message!!)
                    Toast.makeText(context, requireContext().getString(R.string.signup_fail), Toast.LENGTH_LONG).show()
                }
            })
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