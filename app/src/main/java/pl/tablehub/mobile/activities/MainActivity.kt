package pl.tablehub.mobile.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import dagger.hilt.android.AndroidEntryPoint
import pl.tablehub.mobile.R
import androidx.navigation.fragment.NavHostFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.logInFragment) as? NavHostFragment
        if(savedInstanceState == null) {
            navHostFragment?.navController?.navigate(R.id.logInFragment, null,
                NavOptions.Builder().setPopUpTo(R.id.logInFragment, true).build())
        }
    }
}