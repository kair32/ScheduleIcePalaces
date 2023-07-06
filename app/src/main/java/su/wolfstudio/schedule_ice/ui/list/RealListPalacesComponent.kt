package su.wolfstudio.schedule_ice.ui.list

import com.arkivanov.decompose.ComponentContext
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.model.Palaces
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.app.dependencies.getDependency

class RealListPalacesComponent(
    componentContext: ComponentContext,
    val onPalacesChosen: (Long, Boolean) -> Unit
): ComponentContext by componentContext, ListPalacesComponent {

    private val cash = getDependency<ApplicationCache>()

    override val listPalaces: StateValue<List<Palaces>> = cash.listPalaces

    override fun onPalacesClick(palacesId: Long) {
        onPalacesChosen(palacesId, false)
    }

    override fun onPalacesScheduleClick(palacesId: Long) {
        onPalacesChosen(palacesId, true)
    }

}