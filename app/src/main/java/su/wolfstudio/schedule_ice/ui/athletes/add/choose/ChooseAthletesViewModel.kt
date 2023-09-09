package su.wolfstudio.schedule_ice.ui.athletes.add.choose

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Athlete

interface ChooseAthletesViewModel {
    val athletes: StateFlow<List<Athlete>>
}