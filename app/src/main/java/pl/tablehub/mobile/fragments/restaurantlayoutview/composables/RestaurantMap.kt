package pl.tablehub.mobile.fragments.restaurantlayoutview.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import pl.tablehub.mobile.client.model.restaurants.TableStatusChange
import pl.tablehub.mobile.model.v2.Section
import pl.tablehub.mobile.model.v2.TableDetail
import pl.tablehub.mobile.ui.theme.PRIMARY_COLOR
import rememberZoomPanState
import zoomablePannable

@Stable
fun stringToPath(svgPath: String): Path {
    val pathParser = PathParser()
    pathParser.parsePathString(svgPath)
    return pathParser.toPath()
}

@Composable
fun RestaurantMapRenderer(
    section: Section,
    onTableStatusChanged: (TableStatusChange) -> Unit
) {
    val mapPath = stringToPath(section.sectionLayout.shape)
    val transformState = rememberZoomPanState()
    var selectedTableDetail by remember { mutableStateOf<TableDetail?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Canvas(
            modifier = Modifier
                .align(Alignment.TopStart)
                .width(section.sectionLayout.viewportWidth.dp)
                .height(section.sectionLayout.viewportHeight.dp)
                .zoomablePannable(
                    contentWidth = section.sectionLayout.viewportWidth.dp,
                    contentHeight = section.sectionLayout.viewportHeight.dp,
                    state = transformState
                )
        ) {
            drawPath(
                path = mapPath,
                color = PRIMARY_COLOR,
                style = Stroke(width = 4.dp.toPx())
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .width(section.sectionLayout.viewportWidth.dp)
                .height(section.sectionLayout.viewportHeight.dp)
                .graphicsLayer(
                    scaleX = transformState.scale,
                    scaleY = transformState.scale,
                    translationX = transformState.offset.x,
                    translationY = transformState.offset.y
                )
        ) {
            section.tableDetails.forEach { table ->
                TableItem(
                    tableDetail = table,
                    onTableClick = { selectedTableDetail = it }
                )
            }
            section.pois.forEach { poi ->
                PointOfInterest(poi = poi)
            }
            selectedTableDetail?.let { table ->
                TableStatusDialog(
                    tableDetail = table,
                    onDismiss = { selectedTableDetail = null },
                    onStatusChange = { newStatus ->
                        table.tableStatus = newStatus
                        onTableStatusChanged(
                            TableStatusChange(
                                1,
                                sectionId = section.id,
                                tableId = table.id,
                                newStatus
                            )
                        )
                    }
                )
            }
        }
    }
}