package pl.tablehub.mobile.fragments.restaurantlayoutview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import pl.tablehub.mobile.fragments.restaurantlayoutview.composables.RestaurantLayoutMainView
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.ui.shared.constants.NavArgs

@AndroidEntryPoint
class RestaurantLayoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val restaurant = requireArguments().getParcelable(
                    NavArgs.SELECTED_RESTAURANT,
                    Restaurant::class.java
                )
                if (restaurant != null) {
                    RestaurantLayoutMainView(
                        onBack = {
                            findNavController().popBackStack()
                        },
                        restaurant = restaurant
                    )
                }
            }
        }
    }
}
