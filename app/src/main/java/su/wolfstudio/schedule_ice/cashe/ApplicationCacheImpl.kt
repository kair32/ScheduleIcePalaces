package su.wolfstudio.schedule_ice.cashe

import su.wolfstudio.schedule_ice.model.Palaces

class ApplicationCacheImpl : ApplicationCache {
    override val listPalaces: StateValue<List<Palaces>> by stateValue(emptyList())
}