package com.app.compulynx.data.di

import com.app.compulynx.data.repositories.AccountRepositoryImpl
import com.app.compulynx.data.repositories.AuthRepositoryImpl
import com.app.compulynx.data.repositories.TransactionRepositoryImpl
import com.app.compulynx.domain.repositories.AccountRepository
import com.app.compulynx.domain.repositories.AuthRepository
import com.app.compulynx.domain.repositories.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    abstract fun bindAccountRepository(
        accountRepositoryImpl: AccountRepositoryImpl
    ): AccountRepository

    @Binds
    abstract fun bindTransactionRepository(
        transactionRepositoryImpl: TransactionRepositoryImpl
    ): TransactionRepository
}