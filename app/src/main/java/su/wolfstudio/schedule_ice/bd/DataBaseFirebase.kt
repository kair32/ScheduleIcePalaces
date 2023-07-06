package su.wolfstudio.schedule_ice.bd

import su.wolfstudio.schedule_ice.model.Palaces

interface DataBaseFirebase {
    fun getListPalaces() : List<Palaces>
}