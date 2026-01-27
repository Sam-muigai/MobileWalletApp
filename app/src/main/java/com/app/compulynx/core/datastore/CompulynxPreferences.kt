package com.app.compulynx.core.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "compulynx_preferences")


interface CompulynxPreferences {
    suspend fun saveName(name: String)
    fun getName(): Flow<String>
    suspend fun saveEmail(email: String)
    fun getEmail(): Flow<String>

    suspend fun saveAccountNumber(accountNumber: String)
    fun getAccountNumber(): Flow<String>

    suspend fun saveCustomerId(customerId: String)
    fun getCustomerId(): Flow<String>

    suspend fun clearAll()
}


class CompulynxPreferencesImpl(
    private val context: Context
) : CompulynxPreferences {
    override suspend fun saveName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    override fun getName(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[USER_NAME] ?: ""
        }
    }

    override suspend fun saveEmail(email: String) {
        context.dataStore.edit { preferences ->
            preferences[EMAIL] = email
        }
    }

    override fun getEmail(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[EMAIL] ?: ""
        }
    }

    override suspend fun saveAccountNumber(accountNumber: String) {
        context.dataStore.edit { preferences ->
            preferences[ACCOUNT_NUMBER] = accountNumber
        }
    }

    override fun getAccountNumber(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[ACCOUNT_NUMBER] ?: ""
        }
    }

    override suspend fun saveCustomerId(customerId: String) {
        context.dataStore.edit { preferences ->
            preferences[CUSTOMER_ID] = customerId
        }
    }

    override fun getCustomerId(): Flow<String> {
        return context.dataStore.data.map { preferences ->
            preferences[CUSTOMER_ID] ?: ""
        }
    }

    override suspend fun clearAll() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        val EMAIL = stringPreferencesKey("email")
        val USER_NAME = stringPreferencesKey("user_name")
        val ACCOUNT_NUMBER = stringPreferencesKey("account_number")
        val CUSTOMER_ID = stringPreferencesKey("customer_id")
    }


}