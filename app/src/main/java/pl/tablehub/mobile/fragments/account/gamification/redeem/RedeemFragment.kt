package pl.tablehub.mobile.fragments.account.gamification.redeem

import RedeemView
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.model.v2.Reward
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import pl.tablehub.mobile.ui.theme.TableHubTheme
import javax.inject.Inject

@AndroidEntryPoint
class RedeemFragment : Fragment() {

    @Inject
    lateinit var dataStore: EncryptedDataStore

    @Inject
    lateinit var appScope: CoroutineScope

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

                        val timerFlow = remember(reward.id) {
                            dataStore.getRewardTimer(reward.id)
                        }
                        val redeemedUntil by timerFlow.collectAsState(initial = null)

                        val isTimerActive = (redeemedUntil ?: 0L) > System.currentTimeMillis()

                        RedeemView(
                            reward = reward,
                            isTimerActive = isTimerActive,
                            redeemedUntil = redeemedUntil,
                            onBackClick = {
                                findNavController().popBackStack()
                            },
                            onRedeemClick = { rewardToRedeem ->
                                val expiryTime = System.currentTimeMillis() + (15 * 60 * 1000)
                                appScope.launch {
                                    dataStore.saveRewardTimer(rewardToRedeem.id, expiryTime)
                                }

                                // TODO: Implement actual redeem logic (np. wywołanie API)
                                Toast.makeText(context, "Reward Redeemed!", Toast.LENGTH_SHORT).show()
                                findNavController().popBackStack()
                            },
                            onTimerExpired = {
                                appScope.launch {
                                    dataStore.removeRewardTimer(reward.id)
                                }
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