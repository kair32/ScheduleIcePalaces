package su.wolfstudio.schedule_ice.ui.bottom

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import su.wolfstudio.schedule_ice.navigation.BottomNavScreen
import su.wolfstudio.schedule_ice.navigation.Screen
import su.wolfstudio.schedule_ice.ui.theme.SkateColor

@Composable
fun BottomNavigationBar(navController: NavHostController) {

    BottomNavigation(
        backgroundColor = SkateColor
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavScreen.values().forEach { navItem ->
            BottomNavigationItem(
                selected = currentRoute == navItem.route.screenName,
                onClick = {
                    navController.navigate(navItem.route.screenName){
                        popUpTo(navController.graph.startDestinationId){inclusive = true}
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(navItem.icon),
                        contentDescription = stringResource(navItem.label)
                    )
                },
                label = {
                    Text(text = stringResource(navItem.label))
                },
                alwaysShowLabel = false
            )
        }
    }
}