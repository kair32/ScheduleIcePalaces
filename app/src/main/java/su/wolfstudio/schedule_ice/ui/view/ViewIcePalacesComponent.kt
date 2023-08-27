package su.wolfstudio.schedule_ice.ui.view

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Palace

interface ViewIcePalacesComponent {
    val palace : StateFlow<Palace>
    val isSchedule: Boolean

    fun onAddSchedule()
}