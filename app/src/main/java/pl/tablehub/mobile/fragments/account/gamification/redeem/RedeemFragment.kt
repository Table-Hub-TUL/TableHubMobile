package pl.tablehub.mobile.fragments.account.gamification.redeem

import RedeemView
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import pl.tablehub.mobile.ui.theme.TableHubTheme

@AndroidEntryPoint
class RedeemFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            val reward: Reward? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireArguments().getParcelable(NavArgs.SELECTED_REWARD, Reward::class.java)
            } else {
                @Suppress("DEPRECATION")
                requireArguments().getParcelable(NavArgs.SELECTED_REWARD)
            }

            setContent {
                TableHubTheme {
                    if (reward != null) {
                        RedeemView(
                            reward = reward,
                            onBackClick = {
                                findNavController().popBackStack()
                            },
                            onRedeemClick = {
                                // TODO: Implement actual redeem logic
                                Toast.makeText(context, "Reward Redeemed!", Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            }
                        )
                    } else {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}