package pl.tablehub.mobile.fragments.restaurants.mainview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.fragments.restaurants.mainview.composables.MainMapView
import pl.tablehub.mobile.fragments.restaurants.mainview.composables.snackbar.PermissionSnackbar
import pl.tablehub.mobile.model.v1.Location
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import pl.tablehub.mobile.viewmodels.MainViewViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainViewFragment : Fragment() {

    private val viewModel: MainViewViewModel by activityViewModels()

    @Inject
    lateinit var encryptedDataStore: EncryptedDataStore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val onReportSpecific = { restaurant: RestaurantListItem -> findNavController()
            .navigate(R.id.action_mainViewFragment_to_restaurantLayoutFragment,
                bundleOf(Pair(NavArgs.SELECTED_RESTAURANT_ID, restaurant.id)))
        }

        val onMoreDetails: (RestaurantListItem) -> Unit = { restaurant ->
            val searchQuery = Uri.encode(restaurant.name)
            val searchUrl = "https://www.google.com/search?q=$searchQuery"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchUrl))
            context?.startActivity(intent)
        }

        val menuOnClicks: Map<String, () -> Unit> = mapOf(
            "PROFILE" to {
                findNavController().navigate(R.id.action_mainViewFragment_to_profileFragment)
            },
            "REWARDS" to {
                findNavController().navigate(R.id.action_mainViewFragment_to_rewardsFragment)
            },
            "LOGOUT" to {
                lifecycleScope.launch {
                    encryptedDataStore.clearJWT()
                    findNavController().navigate(R.id.action_mainViewFragment_to_logInFragment)
                }
            },
            "SETTINGS" to {
                Toast.makeText(requireContext(), getString(R.string.app_in_dev), Toast.LENGTH_LONG).show()
            }
        )

        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val restaurants = viewModel.restaurants.collectAsState().value.values.toList()
                val restaurantFilters = viewModel.restaurantsFilters.collectAsState().value
                val userLocation by viewModel.userLocation.collectAsState()
                val cuisines by viewModel.cuisines.collectAsState()
                MainMapView(
                    restaurants = restaurants,
                    userLocation = userLocation,
                    onReportGeneral = {
                        findNavController().navigate(R.id.action_mainViewFragment_to_reportViewFragment, bundleOf(
                            Pair(NavArgs.RESTAURANTS, restaurants.toTypedArray()),
                            Pair(NavArgs.USER_LOCATION, userLocation)
                        ))},
                    onReportSpecific = onReportSpecific,
                    onMoreDetails = onMoreDetails,
                    menuOnClicks = menuOnClicks,
                    filters = restaurantFilters,
                    cuisines = cuisines,
                    onRatingChanged = { viewModel.updateFilters(rating = it) },
                    onCuisineSelected = { viewModel.updateFilters(cuisine = it) },
                    onMinFreeSeatsChanged = { viewModel.updateFilters(minSeats = it) },
                    onMapBoundsChanged = { center, radiusInMeters ->
                        val location = Location(center.longitude(), center.latitude())
                        viewModel.updateMapQuery(location, radiusInMeters)
                    }
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