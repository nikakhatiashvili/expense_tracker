package com.example.finaleproject

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.map
import javax.inject.Inject


private val Context._dataStore: DataStore<Preferences> by preferencesDataStore("User_Info")
class DataStoreManager @Inject constructor(context: Context) {

    private val dataStore: DataStore<Preferences> = context._dataStore

    companion object {
        val USER_REMEMBER_KEY = booleanPreferencesKey("BOOLEAN")
    }

    suspend fun storeUser(
        remember: Boolean
    ) {
        dataStore.edit {
            it[USER_REMEMBER_KEY] = remember
        }
    }

    var remember: LiveData<Boolean> = dataStore.data.map {
        it[USER_REMEMBER_KEY] ?: false
    }.asLiveData()

}