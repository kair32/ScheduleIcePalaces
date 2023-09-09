package su.wolfstudio.schedule_ice.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesImpl(
    context: Context
) : Preferences {

    private val preferences: SharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

    override fun getFavoritePalace(): List<Long> {
        return preferences.getStringSet(Preferences.Type.FAVORITE_PALACE.key, emptySet())
            ?.mapNotNull { it.toLongOrNull() }
            ?.toList()
            ?: listOf()
    }

    override fun setFavoritePalace(id: Long) {
        val value = preferences.getStringSet(Preferences.Type.FAVORITE_PALACE.key, emptySet())
            ?.toMutableSet()?: mutableSetOf()
        if (!value.remove(id.toString()))
            value.add(id.toString())
        preferences.edit(true){
            putStringSet(Preferences.Type.FAVORITE_PALACE.key, value)
        }
    }

    private companion object{
        const val prefsName = "AppPreferences"
    }
}