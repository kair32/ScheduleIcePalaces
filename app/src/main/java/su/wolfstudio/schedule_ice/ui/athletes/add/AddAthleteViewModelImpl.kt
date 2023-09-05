package su.wolfstudio.schedule_ice.ui.athletes.add

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.bd.DataBase
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.cashe.stateValue
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.ui.base.ViewModelBase

class AddAthleteViewModelImpl(
    athleteId: Int
) : ViewModelBase(), AddAthleteViewModel {
    private val db = getDependency<DataBase>()

    override val isError: StateValue<Boolean> by stateValue(false)
    override val athlete: StateValue<Athlete> by stateValue(Athlete.getAthlete())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            db.getAthleteById(athleteId)?.let {
                athlete.emit(it)
            }
        }
    }

    override fun onSave() {
        if (checkCompletedFields())
            viewModelScope.launch(Dispatchers.IO) {
                db.updateAthlete(athlete.value)
            }
    }

    override fun updateName(name: String){
        updateAthlete(athlete.value.copy(name = name))
        if (isError.value) checkCompletedFields()
    }
    override fun updateSurname(surname: String){
        updateAthlete(athlete.value.copy(surname = surname))
        if (isError.value) checkCompletedFields()
    }
    override fun updateMiddleName(middleName: String){
        updateAthlete(athlete.value.copy(middleName = middleName))
    }
    override fun updateBirthday(birthday: Long){
        updateAthlete(athlete.value.copy(birthday = birthday))
        if (isError.value) checkCompletedFields()
    }

    private fun checkCompletedFields() : Boolean{
        return if (athlete.value.name.isEmpty() ||
            athlete.value.surname.isEmpty() ||
            athlete.value.birthday == 0L) {
            isError.compareAndSet(isError.value, true)
            false
        } else {
            isError.compareAndSet(isError.value, false)
            true
        }
    }

    private fun updateAthlete(updateAthlete: Athlete) {
        viewModelScope.launch(Dispatchers.IO) {
            athlete.emit(updateAthlete)
        }
    }
}