package su.wolfstudio.schedule_ice.ui.list

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Palace

interface ListPalacesComponent {
    val listPalace : StateFlow<List<Palace>>

    fun onShowSchedule()
    fun onPalacesClick(palacesId: Long)
    fun onPalacesScheduleClick(palacesId: Long)

    fun onFindLine(text: String)

    fun onFavorite(palacesId: Long)
}