package pl.tablehub.mobile.fragments.account.gamification.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.fragments.account.gamification.stats.composables.MyStatisticsView
import pl.tablehub.mobile.ui.theme.TableHubTheme
import androidx.navigation.fragment.findNavController
import pl.tablehub.mobile.R

@AndroidEntryPoint
class MyStatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {
                TableHubTheme {
                    MyStatisticsView(
                        onBackClick = {
                            findNavController().popBackStack()
                        },
                        onRewardsClick = {
                             findNavController().navigate(R.id.action_myStatsFragment_to_rewardsFragment)
                        }
                    )
                }
            }
        }
    }
}