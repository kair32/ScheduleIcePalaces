package su.wolfstudio.schedule_ice.ui.view

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Palaces

interface ViewIcePalacesComponent {
    val palace : StateFlow<Palaces>
    val isSchedule: Boolean

    fun onAddSchedule()
}