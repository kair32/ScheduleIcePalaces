package su.wolfstudio.schedule_ice.cashe

import su.wolfstudio.schedule_ice.model.Palace

interface ApplicationCache {
    val listPalace: StateValue<List<Palace>>
}