package su.wolfstudio.schedule_ice.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomComponent(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController = navController, startDestination = Screen.ListIcePalaces.screenName) {
        composable(Screen.ListIcePalaces.screenName) { ListIcePalacesContainer(Modifier.padding(innerPadding)) }
        composable(Screen.Schedule.screenName) { ScheduleComponent(Modifier.padding(innerPadding)) }
        composable(Screen.Athletes.screenName) { AthletesComponent(Modifier.padding(innerPadding)) }
    }
}