package pl.tablehub.mobile.fragments.account.gamification.achievements

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
import pl.tablehub.mobile.fragments.account.gamification.achievements.composables.AchievementsView
import pl.tablehub.mobile.ui.theme.TableHubTheme
import pl.tablehub.mobile.viewmodels.AchievementsViewModel

@AndroidEntryPoint
class AchievementsFragment : Fragment() {

    private val viewModel: AchievementsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                TableHubTheme {
                    AchievementsView(
                        viewModel = viewModel,

                        onBackClick = {
                            findNavController().popBackStack()
                        }
                    )
                }
            }
        }
    }
}