package pl.tablehub.mobile.fragments.restaurantlayoutview

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.tablehub.mobile.R
import pl.tablehub.mobile.datastore.EncryptedDataStore
import pl.tablehub.mobile.fragments.restaurantlayoutview.composables.RestaurantLayoutMainView
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.fragments.restaurantlayoutview.temp.sampleSections
import pl.tablehub.mobile.model.v2.RestaurantDetail
import pl.tablehub.mobile.model.v2.RestaurantListItem
import pl.tablehub.mobile.ui.shared.constants.NavArgs
import pl.tablehub.mobile.viewmodels.MainViewViewModel
import javax.inject.Inject

@AndroidEntryPoint
class RestaurantLayoutFragment : Fragment() {
    @Inject
    internal lateinit var encryptedDataStore: EncryptedDataStore

    private val viewModel: MainViewViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val restaurant: RestaurantListItem? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requireArguments().getParcelable(NavArgs.SELECTED_RESTAURANT, RestaurantListItem::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    requireArguments().getParcelable(NavArgs.SELECTED_RESTAURANT)
                }
                var restaurantDetail by remember { mutableStateOf<RestaurantDetail?>(null) }
                if (restaurant != null) {
                    LaunchedEffect(restaurant.id) {
                        restaurantDetail = viewModel.getRestaurantById(restaurant.id)
                    }
                }
                val onTableStatusChanged = {update: TableStatusChange -> viewModel.updateTableStatus(update)}
                if (restaurant != null) {
                    RestaurantLayoutMainView(
                        onBack = {
                            findNavController().popBackStack()
                        },
                        onFinishChanges = {
                            findNavController().popBackStack(R.id.mainViewFragment, false)
                        },
                        onTableStatusChanged = onTableStatusChanged,
                        sections = restaurantDetail?.sections ?: emptyList()
                    )
                }
            }
        }
    }
}
