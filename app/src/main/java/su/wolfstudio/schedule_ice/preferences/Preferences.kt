package su.wolfstudio.schedule_ice.preferences

interface Preferences {
    fun getFavoritePalace() : List<Long>
    fun setFavoritePalace(id: Long)

    enum class Type(val key: String){
        FAVORITE_PALACE("PrefFavoritePalace")
    }
}