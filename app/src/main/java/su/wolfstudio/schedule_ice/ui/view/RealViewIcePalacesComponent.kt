package su.wolfstudio.schedule_ice.ui.view

import com.arkivanov.decompose.ComponentContext
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Palace

class RealViewIcePalacesComponent(
    componentContext: ComponentContext,
    private val palacesId: Long,
    override val isSchedule: Boolean,
    private val onPalacesChosen: (Long) -> Unit
): ComponentContext by componentContext, ViewIcePalacesComponent{
    private val cash = getDependency<ApplicationCache>()

    override val palace: StateValue<Palace> by stateValue(cash.listPalace.value.find { it.id == palacesId }!!)

    override fun onAddSchedule() {
        onPalacesChosen.invoke(palacesId)
    }
}