package su.wolfstudio.schedule_ice.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import su.wolfstudio.schedule_ice.ui.list.ListIcePalacesUi
import su.wolfstudio.schedule_ice.ui.view.ViewIcePalacesUi

@Composable
fun PalacesUi(component: PalacesComponent) {
    val childStack by component.childStack.collectAsState()

    Children(childStack){ child ->
        when(val instance = child.instance){
            is PalacesComponent.Child.List -> ListIcePalacesUi(instance.component)
            is PalacesComponent.Child.Details -> ViewIcePalacesUi(instance.component) { component.onBack() }
        }
    }
}