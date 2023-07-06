package su.wolfstudio.schedule_ice.ui

import com.arkivanov.decompose.router.stack.ChildStack
import kotlinx.coroutines.flow.StateFlow
import su.wolfstudio.schedule_ice.ui.list.ListPalacesComponent
import su.wolfstudio.schedule_ice.ui.view.ViewIcePalacesComponent

interface PalacesComponent {
    val childStack: StateFlow<ChildStack<*, Child>>

    fun onBack()

    sealed interface Child{
        class List(val component: ListPalacesComponent) : Child
        class Details(val component: ViewIcePalacesComponent) : Child
    }
}