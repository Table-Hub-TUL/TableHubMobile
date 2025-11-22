package pl.tablehub.mobile.fragments.account.gamification.rewards

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.gamification.rewards.composables.RewardsView
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import pl.tablehub.mobile.ui.theme.TableHubTheme

@AndroidEntryPoint
class RewardsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                TableHubTheme {
                    RewardsView(
                        onBackClick = {
                            findNavController().popBackStack()
                        },
                        onRedeemClick = { reward ->
                            val bundle = bundleOf(NavArgs.SELECTED_REWARD to reward)
                            findNavController().navigate(R.id.action_rewardsFragment_to_redeemFragment, bundle)
                        }
                    )
                }
            }
        }
    }
}