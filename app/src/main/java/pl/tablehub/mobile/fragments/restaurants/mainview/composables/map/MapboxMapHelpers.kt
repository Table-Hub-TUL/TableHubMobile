package pl.tablehub.mobile.fragments.restaurants.mainview.composables.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import pl.tablehub.mobile.model.v1.Section
import pl.tablehub.mobile.model.TableStatus
import pl.tablehub.mobile.model.v2.TableListItem
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

@Composable
fun rememberTextOnBitmap(baseBitmap: Bitmap, text: String): Bitmap {
    return remember(baseBitmap, text) {
        val mutableBitmap = baseBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)
        val paint = Paint().apply {
            color = Color.WHITE
            textSize = baseBitmap.height * 0.33f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
        }
        val x = canvas.width / 2f
        val textBounds = Rect()
        paint.getTextBounds(text, 0, text.length, textBounds)
        val y = (canvas.height / 1.5f) - (textBounds.height() / 2f)
        canvas.drawText(text, x, y, paint)
        mutableBitmap
    }
}

internal fun calculateDistance(
    lat1: Double, lon1: Double,
    lat2: Double, lon2: Double
): Int {
    val r = 6371e3
    val phi1 = lat1 * Math.PI / 180
    val phi2 = lat2 * Math.PI / 180
    val deltaPhi = (lat2 - lat1) * Math.PI / 180
    val deltaLambda = (lon2 - lon1) * Math.PI / 180

    val a = sin(deltaPhi / 2) * sin(deltaPhi / 2) +
            cos(phi1) * cos(phi2) *
            sin(deltaLambda / 2) * sin(deltaLambda / 2)
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    val distance = r * c

    return distance.roundToInt()
}

internal fun calculateFreeTablesText(restaurantId: Long, tables: Map<Long, List<TableListItem>>): String {
    val count: Int = (tables[restaurantId]?.count { it.tableStatus == TableStatus.AVAILABLE }
        ?: 0)
    return when(count < 100) {
        true -> count.toString()
        false -> "99+"
    }
}