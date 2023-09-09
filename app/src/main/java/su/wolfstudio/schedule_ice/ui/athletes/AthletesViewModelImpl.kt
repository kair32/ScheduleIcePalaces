package su.wolfstudio.schedule_ice.ui.athletes

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.bd.DataBase
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.ui.base.ViewModelBase

class AthletesViewModelImpl : ViewModelBase(), AthletesViewModel {
    private val db = getDependency<DataBase>()

    override val athletes: StateValue<List<Athlete>> by stateValue(listOf())

    init {
        viewModelScope.launch(IO) {
            db.getAthletes().collect{
                athletes.emit(it)
            }
        }
    }

    override fun onRemoveAthlete(athleteId: Int) {
        viewModelScope.launch(IO) {
            db.removeAthleteById(athleteId)
        }
    }
}