package my.jcu.edu.au.cp3406.spacelearn.data.preferences

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

val Context.settingsDataStore by preferencesDataStore(
    name = "spacelearn_settings"
)