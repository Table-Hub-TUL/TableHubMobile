package pl.tablehub.mobile.fragments.mainview.composables.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import pl.tablehub.mobile.model.Section
import pl.tablehub.mobile.model.TableStatus

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

internal fun calculateFreeTablesText(restaurantId: Long, tables: Map<Long, List<Section>>): String {
    val count: Int = (tables[restaurantId]?.flatMap { it.tables }?.count { it.status == TableStatus.AVAILABLE }
        ?: 0)
    return when(count < 100) {
        true -> count.toString()
        false -> "99+"
    }
}