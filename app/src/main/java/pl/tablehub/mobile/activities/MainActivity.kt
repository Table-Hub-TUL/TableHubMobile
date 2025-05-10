package pl.tablehub.mobile.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import androidx.navigation.fragment.NavHostFragment
import pl.tablehub.mobile.services.websocket.WebSocketService

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val serviceIntent = Intent(this, WebSocketService::class.java)
        startService(serviceIntent)
        Log.d("MAIN_ACTIVITY", "Called startService() for WebSocketService")
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.logInFragment) as? NavHostFragment
        if(savedInstanceState == null) {
            navHostFragment?.navController?.navigate(R.id.logInFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.logInFragment, true).build())
        }
    }
}