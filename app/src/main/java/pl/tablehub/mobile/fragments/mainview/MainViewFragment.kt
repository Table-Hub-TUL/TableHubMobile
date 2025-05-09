package pl.tablehub.mobile.fragments.mainview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.mainview.composables.MainMapView
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import pl.tablehub.mobile.viewmodels.MainViewViewModel

@AndroidEntryPoint
class MainViewFragment : Fragment() {

    private val viewModel: MainViewViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val restaurants by viewModel.restaurants.collectAsState()
                val userLocation by viewModel.userLocation.collectAsState()
                val tables by viewModel.tables.collectAsState()
                MainMapView(
                    restaurants = restaurants,
                    userLocation = userLocation,
                    tables = tables,
                    onReport = {
                        findNavController().navigate(R.id.action_mainViewFragment_to_reportViewFragment, bundleOf(
                            Pair(NavArgs.RESTAURANTS, restaurants.toTypedArray()),
                            Pair(NavArgs.USER_LOCATION, userLocation)
                        ))
                })
            }
        }
    }
}