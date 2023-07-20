package su.wolfstudio.schedule_ice.ui.list

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.replay
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.model.Palaces
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.cashe.StateValue
import su.wolfstudio.schedule_ice.preferences.Preferences
import su.wolfstudio.schedule_ice.utils.componentCoroutineScope

class RealListPalacesComponent(
    componentContext: ComponentContext,
    val onPalacesChosen: (Long, Boolean) -> Unit
): ComponentContext by componentContext, ListPalacesComponent {

    private val cash = getDependency<ApplicationCache>()
    private val pref = getDependency<Preferences>()

    override val listPalaces: MutableStateFlow<List<Palaces>> = MutableStateFlow(listOf())
    private val listPalacesDef = mutableListOf<Palaces>()
    private val coroutineScope = componentCoroutineScope()

    init {
        getData()
    }
    private fun getData(){
        coroutineScope.launch {
            cash.listPalaces.collect {
                val listFavorite = pref.getFavoritePalace()
                it.map { it.isFavorite = listFavorite.contains(it.id) }
                listPalacesDef.clear()
                listPalacesDef.addAll(it.sortedBy { it.name }.sortedBy { !it.isFavorite })
                listPalaces.emit(listPalacesDef)
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
        listPalaces.compareAndSet(
            expect = listPalaces.value,
            update = listPalacesDef
                .filter {
                    it.name.contains(text, ignoreCase = true)
                }
        )
    }

    override fun onFavorite(palacesId: Long) {
        val listFavorite = pref.getFavoritePalace().toMutableList()
        if (!listFavorite.remove(palacesId))
            listFavorite.add(palacesId)
        pref.setFavoritePalace(palacesId)
        listPalacesDef.map { it.isFavorite = listFavorite.contains(it.id) }
        val list = listPalacesDef
            .sortedBy { it.name }
            .sortedBy { !it.isFavorite }
        listPalaces.compareAndSet(expect = listPalaces.value, update = list)
    }

}