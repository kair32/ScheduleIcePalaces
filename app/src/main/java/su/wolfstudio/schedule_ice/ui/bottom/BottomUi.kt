package su.wolfstudio.schedule_ice.ui.bottom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import su.wolfstudio.schedule_ice.navigation.BottomComponent

@Composable
fun BottomUi(navController: NavHostController) {
    Surface {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(navController = navController)
            },
            content = { innerPadding ->
                BottomComponent(
                    navController = navController,
                    innerPadding = innerPadding
                )
            }
        )
    }
}