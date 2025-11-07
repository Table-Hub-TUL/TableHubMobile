package pl.tablehub.mobile.fragments.account.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.profile.composables.ProfileView
import pl.tablehub.mobile.fragments.account.profile.ProfileViewModel
import pl.tablehub.mobile.ui.theme.TableHubTheme

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                TableHubTheme {
                    ProfileView(
                        viewModel = viewModel,
                        onBackClick = {
                            findNavController().popBackStack()
                        },
                        onLogoutAction = {
                            findNavController().navigate(R.id.action_mainViewFragment_to_logInFragment)
                        }
                    )
                }
            }
        }
    }
}