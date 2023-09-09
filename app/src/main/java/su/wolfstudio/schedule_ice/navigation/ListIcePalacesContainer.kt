package su.wolfstudio.schedule_ice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import su.wolfstudio.schedule_ice.ui.list.ListIcePalacesUi
import su.wolfstudio.schedule_ice.ui.view.ViewIcePalacesUi
import su.wolfstudio.schedule_ice.ui.view.add_schedule.AddScheduleUi

@Composable
fun ListIcePalacesContainer(modifier: Modifier) {
    val navigation = rememberNavController()
    NavHost(navController = navigation, startDestination = Screen.ListIcePalaces.screenName){
        composable(Screen.ListIcePalaces.screenName) { ListIcePalacesUi(modifier = modifier, navigation) }

        composable(
            route = Screen.ViewIcePalaces.screenName + "/{$PALACE_ID_ARG}/{$IS_SCHEDULE_ARG}",
            arguments = listOf(
                navArgument(PALACE_ID_ARG){ type = NavType.LongType },
                navArgument(IS_SCHEDULE_ARG){ type = NavType.BoolType }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong(PALACE_ID_ARG)?.let {
                ViewIcePalacesUi(modifier = modifier, navigation, it, backStackEntry.arguments?.getBoolean(IS_SCHEDULE_ARG)?:false)
            }
        }

        composable(
            route = Screen.AddSchedule.screenName + "/{$PALACE_ID_ARG}",
            arguments = listOf(navArgument(PALACE_ID_ARG){ type = NavType.LongType },)
        ){ backStackEntry ->
            backStackEntry.arguments?.getLong(PALACE_ID_ARG)?.let {
                AddScheduleUi(
                    modifier = modifier,
                    navigation = navigation,
                    palaceId = it
                )
            }
        }
    }
}

const val PALACE_ID_ARG = "palaceId"
const val IS_SCHEDULE_ARG = "isSchedule"