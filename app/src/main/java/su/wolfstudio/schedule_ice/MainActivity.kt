package su.wolfstudio.schedule_ice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import su.wolfstudio.schedule_ice.ui.bottom.BottomUi
import su.wolfstudio.schedule_ice.ui.theme.ScheduleIcePalacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            ScheduleIcePalacesTheme {
                BottomUi(navController = navController)
            }
        }
    }
}