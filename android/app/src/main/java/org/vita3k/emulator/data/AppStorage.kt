package org.vita3k.emulator.data

import android.content.Context
import java.io.File

internal object AppStorage {
    private const val PREFS_NAME = "vita3k_app"
    private const val KEY_INITIAL_SETUP_COMPLETED = "initial_setup_completed"
    private const val KEY_APPS_VIEW_MODE = "apps_view_mode"
    private const val KEY_HIDDEN_COMPAT_APPS = "hidden_compat_apps"
    private const val KEY_HIDE_GAME_TITLES = "hide_game_titles"
    private const val KEY_GLOBAL_HIDE_COMPAT = "global_hide_compat"
    private const val DEFAULT_STORAGE_DIR_NAME = "vita"

    fun storageRootPath(context: Context): String {
        return (context.getExternalFilesDir(null) ?: context.filesDir ?: File(context.cacheDir, "files")).absolutePath
    }

    fun defaultStoragePath(context: Context): String {
        val baseDir = File(storageRootPath(context))
        return File(baseDir, DEFAULT_STORAGE_DIR_NAME).absolutePath
    }

    fun isInitialSetupCompleted(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_INITIAL_SETUP_COMPLETED, false)

    fun setInitialSetupCompleted(context: Context, completed: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_INITIAL_SETUP_COMPLETED, completed)
            .apply()
    }

    fun appsViewMode(context: Context): ViewMode =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getString(KEY_APPS_VIEW_MODE, null)
            ?.let { name -> runCatching { ViewMode.valueOf(name) }.getOrNull() }
            ?: ViewMode.LIST

    fun setAppsViewMode(context: Context, mode: ViewMode) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putString(KEY_APPS_VIEW_MODE, mode.name)
            .apply()
    }

    fun getHiddenCompatApps(context: Context): Set<String> =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getStringSet(KEY_HIDDEN_COMPAT_APPS, emptySet()) ?: emptySet()

    fun setHiddenCompatApps(context: Context, apps: Set<String>) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putStringSet(KEY_HIDDEN_COMPAT_APPS, apps)
            .apply()
    }

    fun hideGameTitles(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_HIDE_GAME_TITLES, false)

    fun setHideGameTitles(context: Context, hide: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_HIDE_GAME_TITLES, hide)
            .apply()
    }

    fun globalHideCompat(context: Context): Boolean =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(KEY_GLOBAL_HIDE_COMPAT, false)

    fun setGlobalHideCompat(context: Context, hide: Boolean) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(KEY_GLOBAL_HIDE_COMPAT, hide)
            .apply()
    }
}