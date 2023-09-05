package su.wolfstudio.schedule_ice.ui.list

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Palace

interface ListPalacesViewModel {
    val listPalace : StateFlow<List<Palace>>

    fun onFindLine(text: String)

    fun onFavorite(palacesId: Long)
}