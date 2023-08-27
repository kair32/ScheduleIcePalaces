package su.wolfstudio.schedule_ice.ui

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import kotlinx.coroutines.flow.StateFlow
import kotlinx.parcelize.Parcelize
import su.wolfstudio.schedule_ice.ui.list.RealListPalacesComponent
import su.wolfstudio.schedule_ice.ui.schedule.RealScheduleComponent
import su.wolfstudio.schedule_ice.ui.view.RealViewIcePalacesComponent
import su.wolfstudio.schedule_ice.ui.view.add_schedule.RealAddScheduleComponent
import su.wolfstudio.schedule_ice.utils.toStateFlow

class RealPalacesComponent(
    componentContext: ComponentContext,
): ComponentContext by componentContext, PalacesComponent {
    private val navigation = StackNavigation<ChildConfig>()

    override val childStack: StateFlow<ChildStack<*, PalacesComponent.Child>> = childStack(
        source = navigation,
        initialConfiguration = ChildConfig.List,
        handleBackButton = true,
        childFactory = ::createChild
    ).toStateFlow(lifecycle)

    override fun onBack(){
        navigation.pop()
    }

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): PalacesComponent.Child = when(config){

        is ChildConfig.AddSchedule ->
            PalacesComponent.Child.AddSchedule(
                RealAddScheduleComponent(componentContext, config.palaceId)
            )

        is ChildConfig.Details ->
            PalacesComponent.Child.Details(
                RealViewIcePalacesComponent(componentContext, config.palacesId, config.isSchedule) { palaceId ->
                    navigation.push(ChildConfig.AddSchedule(palaceId = palaceId))
                }
            )

        is ChildConfig.List ->
            PalacesComponent.Child.List(
                RealListPalacesComponent(
                    componentContext,
                    onPalacesChosen = { palacesId, isSchedule ->
                        navigation.push(ChildConfig.Details(palacesId, isSchedule))
                    },
                    onSchedule = { navigation.push(ChildConfig.Schedule) }
                )
            )

        is ChildConfig.Schedule ->
            PalacesComponent.Child.Schedule(
                RealScheduleComponent(componentContext)
            )
    }

    private sealed interface ChildConfig : Parcelable {

        @Parcelize
        object List: ChildConfig, Parcelable

        @Parcelize
        object Schedule: ChildConfig, Parcelable

        @Parcelize
        data class Details(val palacesId: Long, val isSchedule: Boolean) : ChildConfig, Parcelable

        @Parcelize
        data class AddSchedule(val palaceId: Long) : ChildConfig, Parcelable
    }
}