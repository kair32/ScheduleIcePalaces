package su.wolfstudio.schedule_ice.ui.view

import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Palace
import su.wolfstudio.schedule_ice.ui.base.ViewModelBase

class ViewIcePalacesViewModelImpl(
    private val palacesId: Long,
    override val isSchedule: Boolean,
) : ViewModelBase(), ViewIcePalacesViewModel{
    private val cash = getDependency<ApplicationCache>()
    private val onPalacesChosen: (Long) -> Unit = {}
    override val palace: StateValue<Palace> by stateValue(cash.listPalace.value.find { it.id == palacesId }!!)

    override fun onAddSchedule() {
        onPalacesChosen.invoke(palacesId)
    }
}