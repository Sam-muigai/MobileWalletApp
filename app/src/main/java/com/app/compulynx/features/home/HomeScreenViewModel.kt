package com.app.compulynx.features.home

import androidx.lifecycle.ViewModel
import com.app.compulynx.domain.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

}