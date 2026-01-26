package hr.algebra.to_doapp.util

import android.content.Context
import android.content.SharedPreferences

object SettingsUtil {
    private const val PREF_NAME = "settings_pref"

    fun getPrefs(context: Context): SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveDarkMode(context: Context, isDark: Boolean) {
        getPrefs(context).edit().putBoolean("dark", isDark).apply()
    }

    fun isDarkMode(context: Context): Boolean =
        getPrefs(context).getBoolean("dark", false)
}