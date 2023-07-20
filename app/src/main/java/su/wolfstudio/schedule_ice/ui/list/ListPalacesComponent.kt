package su.wolfstudio.schedule_ice.ui.list

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Palaces

interface ListPalacesComponent {
    val listPalaces : StateFlow<List<Palaces>>

    fun onPalacesClick(palacesId: Long)
    fun onPalacesScheduleClick(palacesId: Long)

    fun onFindLine(text: String)

    fun onFavorite(palacesId: Long)
}