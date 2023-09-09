package su.wolfstudio.schedule_ice.ui.athletes

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Athlete

interface AthletesViewModel {
    val athletes: StateFlow<List<Athlete>>

    fun onRemoveAthlete(athleteId: Int)
}