package pl.tablehub.mobile.fragments.mainview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.mainview.composables.MainMapView
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
            setContent {
                val restaurants by viewModel.restaurants.collectAsState()
                MainMapView(restaurants = restaurants,
                    onReport = {
                        findNavController().navigate(R.id.action_mainViewFragment_to_reportViewFragment)
                    }
                )
            }
        }
    }
}