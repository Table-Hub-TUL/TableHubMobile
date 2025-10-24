package pl.tablehub.mobile.core

import android.app.Application
import android.content.Intent
import java.io.StringWriter
import java.io.PrintWriter
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CrashReportHandler(
    private val application: Application,
) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
        val stackTrace = StringWriter().also { e.printStackTrace(PrintWriter(it)) }.toString()

        val intent = Intent(application, CrashActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra("err", e.message ?: e.javaClass.simpleName)
            putExtra("stack", stackTrace)
            putExtra("thread", t.name)
            putExtra("net", e is SocketTimeoutException || e is UnknownHostException)
        }
        application.startActivity(intent)
        Thread.sleep(100)
    }
}