package com.app.compulynx.features.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.compulynx.domain.repositories.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val accountRepository: AccountRepository
) : ViewModel() {

    private val _profileScreenState = MutableStateFlow(ProfileScreenState())
    val profileScreenState = _profileScreenState.asStateFlow()

    init {
        obtainUserDetails()
    }

    private fun obtainUserDetails() {
        combine(
            accountRepository.getAccountNumber(),
            accountRepository.getEmail(),
            accountRepository.getCustomerId(),
            accountRepository.getUsername()
        ) { accountNumber, email, customerId, username ->
            _profileScreenState.update {
                it.copy(
                    name = username,
                    email = email,
                    customerId = customerId,
                    accountNumber = accountNumber
                )
            }
        }.launchIn(viewModelScope)
    }
}


data class ProfileScreenState(
    val name: String = "",
    val email: String = "",
    val customerId: String = "",
    val accountNumber: String = "",
)