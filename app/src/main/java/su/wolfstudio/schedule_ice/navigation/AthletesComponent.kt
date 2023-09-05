package su.wolfstudio.schedule_ice.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import su.wolfstudio.schedule_ice.ui.athletes.AthletesUi
import su.wolfstudio.schedule_ice.ui.athletes.add.AddAthleteUi

@Composable
fun AthletesComponent(modifier: Modifier) {
    val navigation = rememberNavController()
    NavHost(navController = navigation, startDestination = Screen.Athletes.screenName){
        composable(Screen.Athletes.screenName) { AthletesUi(modifier, navigation) }
        composable(
            route = Screen.AddAthlete.screenName + "/{$ATHLETE_ID_ARG}",
            arguments = listOf(navArgument(ATHLETE_ID_ARG){ type = NavType.IntType },)
        ){ backStackEntry ->
            backStackEntry.arguments?.getInt(ATHLETE_ID_ARG)?.let {
                AddAthleteUi(
                    modifier = modifier,
                    navigation = navigation,
                    athleteId = it
                )
            }
        }
    }
}

const val ATHLETE_ID_ARG = "athleteId"