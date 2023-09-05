package su.wolfstudio.schedule_ice.navigation

import su.wolfstudio.schedule_ice.R

enum class BottomNavScreen(
    val icon: Int,
    val label: Int,
    val route: Screen
){
    LIST_ICE_PALACES(R.drawable.ic_list, R.string.list_palaces, Screen.ListIcePalaces),
    SCHEDULE(R.drawable.ic_calendar, R.string.schedule, Screen.Schedule),
    ATHLETES(R.drawable.ic_figure_skating, R.string.athletes, Screen.Athletes),
}