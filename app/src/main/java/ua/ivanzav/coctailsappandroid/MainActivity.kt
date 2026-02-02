package ua.ivanzav.coctailsappandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import ua.ivanzav.coctailsappandroid.ui.RootApplication
import ua.ivanzav.coctailsappandroid.ui.theme.CoctailsAppAndroidTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoctailsAppAndroidTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    RootApplication()
                }
            }
        }
    }
}