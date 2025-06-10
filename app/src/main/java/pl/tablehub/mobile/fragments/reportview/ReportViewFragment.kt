package pl.tablehub.mobile.fragments.reportview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.reportview.composables.MainReportView
import pl.tablehub.mobile.model.Location
import pl.tablehub.mobile.model.Restaurant
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
                val restaurants: List<Restaurant> =
                    (arguments?.getParcelableArray(NavArgs.RESTAURANTS, Restaurant::class.java)?.map { it as Restaurant }
                        ?: emptyList())
                val userLocation: Location =
                    arguments?.getParcelable(NavArgs.USER_LOCATION, Location::class.java)!!
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