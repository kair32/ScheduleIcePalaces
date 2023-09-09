package su.wolfstudio.schedule_ice.model

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class ListPalaces(
    val items: List<Palace> = emptyList()
)

@IgnoreExtraProperties
data class Palace(
    val id: Long = -1,
    val name: String = "",
    val url: String = "",
    val urlSchedule: String = "",
    val urlRoute: String? = null,
    var isFavorite: Boolean = false
)