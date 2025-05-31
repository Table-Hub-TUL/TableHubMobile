package pl.tablehub.mobile.fragments.restaurantlayoutview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.fragments.restaurantlayoutview.composables.RestaurantLayoutMainView
import pl.tablehub.mobile.model.Restaurant
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantLayoutFragment : Fragment() {
    @Inject
    lateinit var encryptedDataStore: EncryptedDataStore

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
                        onFinishChanges = {
                            lifecycleScope.launch {
                                findNavController().navigate(
                                    R.id.action_restaurantLayoutFragment_to_mainViewFragment,
                                    bundleOf(Pair(NavArgs.JWT, encryptedDataStore.getJWT().first()!!))
                                )
                            }
                        },
                        restaurant = restaurant
                    )
                }
            }
        }
    }
}
