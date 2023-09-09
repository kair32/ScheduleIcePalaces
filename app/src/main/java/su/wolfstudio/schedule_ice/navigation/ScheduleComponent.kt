package su.wolfstudio.schedule_ice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import su.wolfstudio.schedule_ice.ui.schedule.ScheduleUi

@Composable
fun ScheduleComponent(modifier: Modifier) {
    val navigation = rememberNavController()
    NavHost(navController = navigation, startDestination = Screen.Schedule.screenName){
        composable(Screen.Schedule.screenName) { ScheduleUi(modifier, navigation) }
    }
}