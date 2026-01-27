package com.app.compulynx.core.network.di

import com.app.compulynx.core.network.CompulynxApiService
import com.app.compulynx.core.network.CompulynxApiServiceImpl
import com.app.compulynx.core.network.getKtorClient
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideCompulynxApiService(): CompulynxApiService {
        return CompulynxApiServiceImpl(provideKtorClient())
    }

    @Provides
    fun provideKtorClient(): HttpClient {
        return getKtorClient()
    }
}