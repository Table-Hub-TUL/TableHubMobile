package pl.tablehub.mobile.fragments.mainview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                MapboxMap(
                    Modifier.fillMaxSize(),
                    mapViewportState = rememberMapViewportState {
                        setCameraOptions {
                            zoom(2.0)
                            center(Point.fromLngLat(-98.0, 39.5))
                            pitch(0.0)
                            bearing(0.0)
                        }
                    },
                )
            }
        }
    }
}