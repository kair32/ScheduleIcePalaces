package su.wolfstudio.schedule_ice.ui

import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.ui.list.ListPalacesComponent
import su.wolfstudio.schedule_ice.ui.schedule.ScheduleComponent
import su.wolfstudio.schedule_ice.ui.view.ViewIcePalacesComponent
import su.wolfstudio.schedule_ice.ui.view.add_schedule.AddScheduleComponent

interface PalacesComponent {
    val childStack: StateFlow<ChildStack<*, Child>>

    fun onBack()

    sealed interface Child{
        class List(val component: ListPalacesComponent) : Child
        class Schedule(val component: ScheduleComponent) : Child
        class Details(val component: ViewIcePalacesComponent) : Child
        class AddSchedule(val component: AddScheduleComponent) : Child
    }
}