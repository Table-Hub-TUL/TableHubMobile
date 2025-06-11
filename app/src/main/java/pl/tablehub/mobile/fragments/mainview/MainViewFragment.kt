package pl.tablehub.mobile.fragments.mainview

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.mainview.composables.MainMapView
import pl.tablehub.mobile.fragments.mainview.composables.snackbar.PermissionSnackbar
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import pl.tablehub.mobile.viewmodels.MainViewViewModel

@AndroidEntryPoint
class MainViewFragment : Fragment() {

    private val viewModel: MainViewViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val onReportSpecific = {restaurant: Restaurant -> findNavController()
            .navigate(R.id.action_mainViewFragment_to_restaurantLayoutFragment,
                bundleOf(Pair(NavArgs.SELECTED_RESTAURANT, restaurant)))
        }

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val restaurants = viewModel.restaurants.collectAsState().value.values.toList()
                val userLocation by viewModel.userLocation.collectAsState()
                MainMapView(
                    restaurants = restaurants,
                    userLocation = userLocation,
                    onReportGeneral = {
                        findNavController().navigate(R.id.action_mainViewFragment_to_reportViewFragment, bundleOf(
                            Pair(NavArgs.RESTAURANTS, restaurants.toTypedArray()),
                            Pair(NavArgs.USER_LOCATION, userLocation)
                    ))},
                    onReportSpecific = onReportSpecific
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        handlePermissions()
    }

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (!(fineGranted || coarseGranted)) {
            PermissionSnackbar(requireView(), requireContext(), R.string.location_denied).show()
        } else {
            viewModel.fetchUserLocation()
        }
    }

    private fun requestPermissions() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun handlePermissions() {
        if(ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission_group.LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions()
        }
    }
}