package pl.tablehub.mobile.core

import com.rollbar.android.Rollbar
import android.app.Application
import pl.tablehub.mobile.util.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class TableHubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Rollbar.init(
            this,
            Constants.ROLLBAR_TOKEN,
            Constants.APP_ENV
        )
        val customHandler = CrashReportHandler(this)
        Thread.setDefaultUncaughtExceptionHandler(customHandler)
    }
}