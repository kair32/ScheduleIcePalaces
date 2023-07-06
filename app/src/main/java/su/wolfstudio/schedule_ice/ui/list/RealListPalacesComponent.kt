package su.wolfstudio.schedule_ice.ui.list

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.model.Palaces
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.utils.componentCoroutineScope

class RealListPalacesComponent(
    componentContext: ComponentContext,
    val onPalacesChosen: (Long, Boolean) -> Unit
): ComponentContext by componentContext, ListPalacesComponent {

    private val cash = getDependency<ApplicationCache>()

    override val listPalaces: MutableStateFlow<List<Palaces>> = MutableStateFlow(listOf())
    private val listPalacesDef = mutableListOf<Palaces>()
    private val coroutineScope = componentCoroutineScope()

    init {
        getData()
    }
    private fun getData(){
        coroutineScope.launch {
            cash.listPalaces.collect {
                listPalacesDef.addAll(it)
                listPalaces.emit(it)
            }
        }
    }

    override fun onPalacesClick(palacesId: Long) {
        onPalacesChosen(palacesId, false)
    }

    override fun onPalacesScheduleClick(palacesId: Long) {
        onPalacesChosen(palacesId, true)
    }

    override fun onFindLine(text: String) {
        listPalaces.compareAndSet(listPalaces.value,
            listPalacesDef.filter { it.name.contains(text, ignoreCase = true) }
        )
    }

}