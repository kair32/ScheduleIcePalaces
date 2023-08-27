package su.wolfstudio.schedule_ice.cashe

import su.wolfstudio.schedule_ice.model.Palace

class ApplicationCacheImpl : ApplicationCache {
    override val listPalace: StateValue<List<Palace>> by stateValue(emptyList())
}