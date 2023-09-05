package su.wolfstudio.schedule_ice.navigation

sealed class Screen(val screenName: String) {
    object ListIcePalaces: Screen("list_ice_palaces")

    object Schedule: Screen("schedule")

    object Athletes: Screen("athletes")
    object AddAthlete: Screen("add_athletes")
    object ViewIcePalaces: Screen("view_ice_palaces")
    object AddSchedule: Screen("add_schedule")
}