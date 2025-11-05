package pl.tablehub.mobile.activities

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.compose.ui.graphics.toArgb
import com.rollbar.android.Rollbar
import pl.tablehub.mobile.R
import pl.tablehub.mobile.ui.theme.GREEN_FREE_COLOR
import pl.tablehub.mobile.ui.theme.TERTIARY_COLOR
import kotlin.system.exitProcess

class CrashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val errorMessage = intent.getStringExtra("err") ?: getString(R.string.crash_unknown_error)
        val stackTrace = intent.getStringExtra("stack") ?: ""
        val isNetwork = intent.getBooleanExtra("net", false)
        val threadName = intent.getStringExtra("thread") ?: getString(R.string.crash_unknown_thread)

        val message = if (isNetwork) {
            getString(R.string.crash_message_network)
        } else {
            getString(R.string.crash_message_general)
        }

        val dialog = AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog_Alert)
            .setTitle(R.string.crash_title)
            .setMessage(message)
            .setPositiveButton(R.string.crash_button_report) { _, _ ->
                try {
                    val exception = Exception(errorMessage)
                    Rollbar.instance().error(exception, mapOf(
                        "stackTrace" to stackTrace,
                        "threadName" to threadName,
                        "isNetworkError" to isNetwork
                    ))
                    Log.d("CrashActivity", "Full crash report sent to Rollbar")
                } catch (e: Exception) {
                    Log.e("CrashActivity", "Failed to report to Rollbar", e)
                }
                terminateApp()
            }
            .setNegativeButton(R.string.crash_button_close) { _, _ ->
                Log.e("CrashActivity", "App crashed (not reported): $errorMessage\n$stackTrace")
                terminateApp()
            }
            .setCancelable(false)
            .create()

        dialog.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.apply {
            setTextColor(GREEN_FREE_COLOR.toArgb())
            textSize = 16f
        }
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.apply {
            setTextColor(TERTIARY_COLOR.toArgb())
        }
    }

    private fun terminateApp() {
        finish()
        Process.killProcess(Process.myPid())
        exitProcess(10)
    }
}