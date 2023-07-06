package su.wolfstudio.schedule_ice.cashe

import su.wolfstudio.schedule_ice.model.Palaces

interface ApplicationCache {
    val listPalaces: StateValue<List<Palaces>>
}