package su.wolfstudio.schedule_ice.ui.athletes.add.choose

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.bd.DataBase
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.ui.base.ViewModelBase

class ChooseAthletesViewModelImpl : ViewModelBase(), ChooseAthletesViewModel {
    private val db = getDependency<DataBase>()

    override val athletes: StateValue<List<Athlete>> by stateValue(listOf())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            db.getAthletes().collect{
                athletes.emit(it)
            }
        }
    }
}