package su.wolfstudio.schedule_ice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import com.google.firebase.FirebaseApp
import su.wolfstudio.schedule_ice.ui.PalacesUi
import su.wolfstudio.schedule_ice.ui.RealPalacesComponent
import su.wolfstudio.schedule_ice.ui.theme.ScheduleIcePalacesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = RealPalacesComponent(defaultComponentContext())
        setContent {
            ScheduleIcePalacesTheme {
                PalacesUi(rootComponent)
            }
        }
    }
}