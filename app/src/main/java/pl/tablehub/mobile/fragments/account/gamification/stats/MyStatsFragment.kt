package pl.tablehub.mobile.fragments.account.gamification.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.account.gamification.stats.composables.MyStatsView
import pl.tablehub.mobile.ui.theme.TableHubTheme
import pl.tablehub.mobile.viewmodels.MyStatsViewModel

@AndroidEntryPoint
class MyStatsFragment : Fragment() {

    private val viewModel: MyStatsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TableHubTheme {
                    MyStatsView(
                        viewModel = viewModel,

                        onBackClick = { findNavController().popBackStack() },

                        onRewardsClick = {
                            findNavController().navigate(R.id.action_myStatsFragment_to_rewardsFragment)
                        },

                        onRankingsClick = {
                        },

                        onAchievementsClick = {
                            findNavController().navigate(R.id.action_myStatsFragment_to_achievementsFragment)
                        }
                    )
                }
            }
        }
    }
}