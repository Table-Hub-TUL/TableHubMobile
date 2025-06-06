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
import pl.tablehub.mobile.client.IAuthService
import pl.tablehub.mobile.client.RetrofitClient
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
                        Toast.makeText(context, "Signup successful!", Toast.LENGTH_SHORT).show()
                        navigateToLogin()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Toast.makeText(context, "Signup failed: ${errorBody ?: "Unknown error"}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Log.d("ERORR", t.message!!)
                    Toast.makeText(context, "Signup error: ${t.message}", Toast.LENGTH_LONG).show()
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