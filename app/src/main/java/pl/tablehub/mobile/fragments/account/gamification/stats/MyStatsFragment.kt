package pl.tablehub.mobile.fragments.account.gamification.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.fragments.account.gamification.stats.composables.MyStatisticsView

@AndroidEntryPoint
class MyStatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MyStatisticsView()
            }
        }
    }
}