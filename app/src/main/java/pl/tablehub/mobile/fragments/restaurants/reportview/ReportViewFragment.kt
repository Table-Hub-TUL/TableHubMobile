package pl.tablehub.mobile.fragments.restaurants.reportview

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.restaurants.reportview.composables.MainReportView
import pl.tablehub.mobile.model.v1.Location
import pl.tablehub.mobile.model.v1.Restaurant
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.ui.shared.constants.NavArgs

@AndroidEntryPoint
class ReportViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val restaurants: List<RestaurantListItem> =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requireArguments().getParcelableArray(NavArgs.RESTAURANTS, RestaurantListItem::class.java)
                            ?.map { it as RestaurantListItem } ?: emptyList()
                    } else {
                        @Suppress("DEPRECATION")
                        requireArguments().getParcelableArray(NavArgs.RESTAURANTS)
                            ?.map { it as RestaurantListItem } ?: emptyList()
                    }

                val userLocation: Location =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        requireArguments().getParcelable(NavArgs.USER_LOCATION, Location::class.java)!!
                    } else {
                        @Suppress("DEPRECATION")
                        requireArguments().getParcelable(NavArgs.USER_LOCATION)!!
                    }
                MainReportView (
                    onBack = {
                        findNavController().navigate(R.id.action_reportViewFragment_to_mainViewFragment2)
                    },
                    restaurants = restaurants,
                    userLocation = userLocation,
                    onRestaurantSelected = { restaurant ->
                        findNavController().navigate(R.id.action_reportViewFragment_to_restaurantLayoutFragment, bundleOf(
                            Pair(NavArgs.SELECTED_RESTAURANT, restaurant)
                        ))
                    }
                )
            }
        }
    }
}