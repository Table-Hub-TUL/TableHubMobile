package pl.tablehub.mobile.fragments.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
<<<<<<< HEAD
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.login.composables.MainLoginView
=======
import pl.tablehub.mobile.fragments.login.composables.MainLoginView
import pl.tablehub.mobile.ui.theme.TableHubTheme
>>>>>>> 6b09a05c76ebb26650abc485a850eff51d5be7ac

@AndroidEntryPoint
class LogInFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
<<<<<<< HEAD
                MainLoginView(
                    onRegister = {
                        findNavController().navigate(R.id.action_logInFragment_to_mainViewFragment)
                    }
                )
=======
                MainLoginView()
>>>>>>> 6b09a05c76ebb26650abc485a850eff51d5be7ac
            }
        }
    }
}