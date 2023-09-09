package su.wolfstudio.schedule_ice.ui.athletes.add

import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.model.Athlete
import su.wolfstudio.schedule_ice.model.LoadStatus

interface AddAthleteViewModel {
    val status: StateFlow<LoadStatus>
    val isError: StateFlow<Boolean>
    val athlete: StateFlow<Athlete>
    fun onSave()
    fun updateName(name: String)
    fun updateSurname(surname: String)
    fun updateMiddleName(middleName: String)
    fun updateBirthday(birthday: Long)
}