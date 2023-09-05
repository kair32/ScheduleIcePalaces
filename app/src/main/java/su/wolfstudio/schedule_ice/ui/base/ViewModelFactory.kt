package su.wolfstudio.schedule_ice.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import su.wolfstudio.schedule_ice.ui.athletes.add.AddAthleteViewModelImpl
import su.wolfstudio.schedule_ice.ui.view.ViewIcePalacesViewModelImpl
import su.wolfstudio.schedule_ice.ui.view.add_schedule.AddScheduleViewModelImpl

class ViewIcePalacesViewModelFactory(private val palacesId: Long, private val isSchedule: Boolean) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        ViewIcePalacesViewModelImpl(palacesId = palacesId, isSchedule = isSchedule) as T
}

class AddScheduleViewModelFactory(private val palacesId: Long) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AddScheduleViewModelImpl(palacesId = palacesId) as T
}

class AddAthleteViewModelFactory(private val athleteId: Int) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        AddAthleteViewModelImpl(athleteId = athleteId) as T
}