package pl.tablehub.mobile.fragments.mainview.composables.snackbar

import android.content.Context
import android.view.Gravity
import android.view.View
import android.graphics.Color
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import pl.tablehub.mobile.R

fun PermissionSnackbar(view: View, context: Context, stringRes: Int): Snackbar {
    val snackbar = Snackbar.make(view, context.getString(stringRes), Snackbar.LENGTH_LONG)
    val snackbarView = snackbar.view
    snackbarView.background = ContextCompat.getDrawable(context, R.drawable.snackbar_background)
    snackbar.setTextColor(Color.WHITE)
    snackbar.setActionTextColor(Color.WHITE)
    val params = snackbarView.layoutParams
    if (params is CoordinatorLayout.LayoutParams) {
        params.gravity = Gravity.CENTER
    } else if (params is FrameLayout.LayoutParams) {
        params.gravity = Gravity.CENTER
    }
    snackbarView.layoutParams = params
    return snackbar
}