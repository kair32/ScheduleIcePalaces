package su.wolfstudio.schedule_ice.ui.list

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import su.wolfstudio.schedule_ice.cashe.ApplicationCache
import su.wolfstudio.schedule_ice.model.Palace
import su.wolfstudio.schedule_ice.app.dependencies.getDependency
import su.wolfstudio.schedule_ice.preferences.Preferences
import su.wolfstudio.schedule_ice.ui.base.ViewModelBase

class ListPalacesViewModelImpl : ViewModelBase(), ListPalacesViewModel {

    private val cash = getDependency<ApplicationCache>()
    private val pref = getDependency<Preferences>()

    override val listPalace: MutableStateFlow<List<Palace>> = MutableStateFlow(listOf())
    private val listPalaceDef = mutableListOf<Palace>()

    init {
        getData()
    }
    private fun getData(){
        viewModelScope.launch {
            cash.listPalace.collect { list ->
                val listFavorite = pref.getFavoritePalace()
                list.map { it.isFavorite = listFavorite.contains(it.id) }
                listPalaceDef.clear()
                listPalaceDef.addAll(list.sortedBy { it.name }.sortedBy { !it.isFavorite })
                listPalace.emit(listPalaceDef)
            }
        }
    }

    override fun onFindLine(text: String) {
        listPalace.compareAndSet(
            expect = listPalace.value,
            update = listPalaceDef
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
        listPalaceDef.map { it.isFavorite = listFavorite.contains(it.id) }
        val list = listPalaceDef
            .sortedBy { it.name }
            .sortedBy { !it.isFavorite }
        listPalace.compareAndSet(expect = listPalace.value, update = list)
    }

}