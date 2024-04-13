package com.ifs21020.lostandfounds.di

import android.content.Context
import com.ifs21020.lostandfounds.data.pref.UserPreference
import com.ifs21020.lostandfounds.data.pref.dataStore
import com.ifs21020.lostandfounds.data.remote.retrofit.ApiConfig
import com.ifs21020.lostandfounds.data.remote.retrofit.IApiService
import com.ifs21020.lostandfounds.data.repository.AuthRepository
import com.ifs21020.lostandfounds.data.repository.LFRepository
import com.ifs21020.lostandfounds.data.repository.UserRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {

    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService: IApiService = ApiConfig.getApiService(user.token)
        return AuthRepository.getInstance(pref, apiService)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService: IApiService = ApiConfig.getApiService(user.token)
        return UserRepository.getInstance(apiService)
    }

    fun provideLFRepository(context: Context): LFRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val user = runBlocking { pref.getSession().first() }
        val apiService: IApiService = ApiConfig.getApiService(user.token)
        return LFRepository.getInstance(apiService)
    }
}
